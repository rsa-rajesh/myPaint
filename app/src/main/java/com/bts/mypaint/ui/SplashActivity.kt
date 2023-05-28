package com.bts.mypaint.ui

import android.os.Bundle
import androidcommon.base.BaseActivity
import androidcommon.extension.showErrorDialog
import androidcommon.utils.UiState
import androidx.lifecycle.lifecycleScope
import com.bts.mypaint.data.Prefs
import com.bts.mypaint.databinding.ActivitySplashBinding
import com.bts.mypaint.ui.colorGenerate.FandeckActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.thread

class SplashActivity : BaseActivity() {
    private val prefs by inject<Prefs>()
    private var TAG = "Kotlin_Test"

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    lateinit var appID: String
    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launchWhenCreated {
            delay(3000)
            if (prefs.isColorsDownloaded){
                startActivity(FandeckActivity.newIntent(this@SplashActivity))
                this@SplashActivity.finish()
            }else{
                serverDetailsObserver()
                homeViewModel.getColorantsFromServer()
            }
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
                            startActivity(FandeckActivity.newIntent(this@SplashActivity))
                            this@SplashActivity.finish()
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

}