package com.bts.mypaint.ui.colorGenerate

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidcommon.RDrawable
import androidcommon.base.BaseActivity
import androidcommon.extension.showErrorDialog
import androidcommon.extension.toastS
import androidcommon.utils.UiState
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bts.mypaint.adapters.FandeckListAdapter
import com.bts.mypaint.adapters.ProductListAdapter
import com.bts.mypaint.data.Prefs
import com.bts.mypaint.databinding.ActivityFandeckBinding
import com.bts.mypaint.ui.HomeViewModel
import com.bts.mypaint.ui.about.AboutAppActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.thread

class FandeckActivity : BaseActivity() {

    private val homeViewModel by viewModel<HomeViewModel>()
    private val binding by lazy {
        ActivityFandeckBinding.inflate(layoutInflater)
    }
    private val prefs by inject<Prefs>()
    private lateinit var fandeckListAdapter: FandeckListAdapter
    private lateinit var productListAdapter: ProductListAdapter
    var menuVisible = false

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FandeckActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        homeViewModel.getFandecks()
        observeData()

        binding.root.setOnClickListener {
            binding.constraintLayout.transitionToStart()
            menuVisible = false
        }

        binding.ivManu.setOnClickListener {
            menuVisible = if (menuVisible) {
                binding.constraintLayout.transitionToStart()
                menuVisible = false
                false
            } else {
                binding.constraintLayout.transitionToEnd()
                menuVisible = true
                true
            }
        }
        binding.llAbout.setOnClickListener {
            startActivity(AboutAppActivity.newIntent(this@FandeckActivity))
        }
        binding.llColorantPrice.setOnClickListener {
            toastS("Coming Soon...")
        }
        binding.llUpdate.setOnClickListener {
            serverDetailsObserver()
            homeViewModel.getColorantsFromServer()
        }
    }

    private fun serverDetailsObserver() {
        homeViewModel.colorantsFromServer.observe(this) {
            it ?: return@observe
            binding.apply {
                when (it) {
                    is UiState.Loading -> {
                        showProgress("Downloading Colorants")
                    }

                    is UiState.Success -> {
                        showProgress("Saving Colorants")
                        thread {
                            homeViewModel.deleteColorants()
                            for (colorant in it.data!!) {
                                homeViewModel.insert(colorant)
                            }
                        }
                        lifecycleScope.launch {
                            delay(1000)
                            homeViewModel.getColorsFromServer()
                        }
                    }

                    is UiState.Error -> {
                        hideProgress()
                        showErrorDialog(
                            isCancellable = false,
                            message = "सर्भरमा जडान गर्न सकेन!!! \n कृपया फेरि प्रयास गर्नुहोस्",
                            title = "सर्भरमा जडान गर्न सकेन!!!",
                            label = "फेरि प्रयास गर्नुहोस्",
                            onBtnClick = { retryClicked() })
                    }

                    else -> {
                        showErrorDialog(
                            isCancellable = false,
                            message = "सर्भरमा जडान गर्न सकेन!!! \n कृपया फेरि प्रयास गर्नुहोस्",
                            title = "सर्भरमा जडान गर्न सकेन!!!",
                            label = "फेरि प्रयास गर्नुहोस्",
                            onBtnClick = { retryClicked() })
                    }
                }
            }
        }

        homeViewModel.colorsFromServer.observe(this) {
            it ?: return@observe
            binding.apply {
                when (it) {
                    is UiState.Loading -> {
                        showProgress("Downloading Colors...")
                    }

                    is UiState.Success -> {
                        showProgress("Saving Colors...")
                        thread {
                            homeViewModel.deleteColors()
                            for (colors in it.data!!) {
                                homeViewModel.insert(colors)
                            }
                        }
                        lifecycleScope.launch {
                            delay(2000)
                            hideProgress()
                            prefs.isColorsDownloaded = true

                            showErrorDialog(
                                icon = RDrawable.ic_success_for_dilog,
                                isCancellable = false,
                                color = Color.GREEN,
                                title = "Success",
                                message = "Successfully updated all colors and colorants",
                                label = "Ok",
                            )
                        }

                    }

                    is UiState.Error -> {
                        hideProgress()
                        showErrorDialog(
                            isCancellable = false,
                            message = "Couldn't connect to server!!! \nPlease check your internet connection and Try again",
                            title = "Couldn't connect to server!!!",
                            label = "Try again",
                            onBtnClick = { retryClicked() })
                    }

                    else -> {
                        showErrorDialog(
                            isCancellable = false,
                            message = "Couldn't connect to server!!! \nPlease check your internet connection and Try again",
                            title = "Couldn't connect to server!!!",
                            label = "Try again",
                            onBtnClick = { retryClicked() })
                    }
                }
            }
        }
    }

    private fun retryClicked() {
        homeViewModel.getColorantsFromServer()
    }

    private fun observeData() {
        homeViewModel.fandecksList.observe(this) {
            it ?: return@observe
            if (it.isNotEmpty()) {
                fandeckListAdapter = FandeckListAdapter(onItemClick = { card ->
                    homeViewModel.getProducts(card.card_name)
                    observeData()
                    binding.constraintLayout.transitionToStart()
                    menuVisible = false
                }, pref = prefs)
                fandeckListAdapter.items = it
                binding.rvFandeck.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                binding.rvFandeck.adapter = fandeckListAdapter
            }
        }

        homeViewModel.productsList.observe(this) {
            it ?: return@observe
            if (it.isNotEmpty()) {
                productListAdapter = ProductListAdapter(onItemClick = { product ->
                    startActivity(product.card_name?.let { it1 ->
                        product.product_name?.let { it2 ->
                            product.image?.let { it3 ->
                                ColorSelectionActivity.newIntent(
                                    this@FandeckActivity,
                                    it1, it2, it3
                                )
                            }
                        }
                    })
                    binding.constraintLayout.transitionToStart()
                    menuVisible = false
                }, pref = prefs)
                productListAdapter.items = it
                binding.rvProducts.layoutManager =
                    LinearLayoutManager(this)
                binding.rvProducts.adapter = productListAdapter
            }
        }

    }
}