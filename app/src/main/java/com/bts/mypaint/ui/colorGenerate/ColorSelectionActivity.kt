package com.bts.mypaint.ui.colorGenerate

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidcommon.RLayout
import androidcommon.base.BaseActivity
import androidcommon.extension.load
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bts.mypaint.adapters.ColorantsListAdapter
import com.bts.mypaint.adapters.ColorsDropdownAdapterV2
import com.bts.mypaint.data.Prefs
import com.bts.mypaint.databinding.ActivityColorSelectionBinding
import com.bts.mypaint.domain.dbmodel.TblColors
import com.bts.mypaint.ui.HomeViewModel
import com.google.android.material.chip.Chip
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ColorSelectionActivity : BaseActivity() {

    private val homeViewModel by viewModel<HomeViewModel>()

    private val binding by lazy {
        ActivityColorSelectionBinding.inflate(layoutInflater)
    }
    private val prefs by inject<Prefs>()

    private lateinit var colorsDropdownAdapter: ColorsDropdownAdapterV2

    private val fandeck by lazy {
        intent.getStringExtra(_Fandeck)
    }

    private val product by lazy {
        intent.getStringExtra(_Product)
    }

    private val image by lazy {
        intent.getStringExtra(_Image)
    }
    private lateinit var selectedColor: TblColors
    private var selectedQty: Float = 0f
    private lateinit var colorantsListAdapter: ColorantsListAdapter


    companion object {
        private const val _Fandeck = "fandeck"
        private const val _Product = "product"
        private const val _Image = "image"
        fun newIntent(context: Context, fandeck: String, product: String, image: String): Intent {
            return Intent(context, ColorSelectionActivity::class.java).apply {
                putExtra(_Product, product)
                putExtra(_Fandeck, fandeck)
                putExtra(_Image, image)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        homeViewModel.getColors(fandeck, product)
        observeData()
        binding.tvFandeck.text = fandeck!!.uppercase()
        binding.tvProduct.text = product
        binding.toolbar.tvToolbarTitle.text = "Color Formulae"
        binding.toolbar.ivBack.setOnClickListener {
            startActivity(FandeckActivity.newIntent(this@ColorSelectionActivity))
            this@ColorSelectionActivity.finish()
        }
        binding.ivImage.load(image)
    }

    private fun observeData() {
        homeViewModel.colorsList?.observe(this) {
            it ?: return@observe
            if (it.isNotEmpty()) {
                colorsDropdownAdapter =
                    ColorsDropdownAdapterV2(this, it)
                binding.actColor.setAdapter(colorsDropdownAdapter)
                binding.actColor.onItemClickListener =
                    OnItemClickListener { adapterView, view, pos, id -> //this is the way to find selected object/item
                        selectedColor = adapterView.getItemAtPosition(pos) as TblColors
                        colorSelected()
                        binding.cvFormulae.isGone = true
                    }
            }
        }
    }

    private fun observeCalcu() {
        homeViewModel.calcuList.observe(this) {
            it ?: return@observe
            if (it.isNotEmpty()) {
                colorantsListAdapter = ColorantsListAdapter(prefs)
                colorantsListAdapter.items = it
                binding.rvColorants.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                binding.rvColorants.adapter = colorantsListAdapter
                var total = 0f
                for (a in it) {
                    total += (a.qnt * (a.tblColorants!!.unit_price / 1000))
                }
                binding.tvTotal.text = "Total: Rs.${String.format("%.3f", total)} /-"
                binding.cvFormulae.isVisible = true
                binding.tvBase.text="Base: ${selectedColor.new_base}"
            }
        }
    }

    private fun colorSelected() {
        binding.tvCalcuTitle1.isVisible = true
        binding.cgType.isVisible = true
        binding.cvSelected.isVisible = true
        hideKeyboard(currentFocus ?: View(this))

        binding.cvSelected.setCardBackgroundColor(Color.argb(255,selectedColor.red,
            selectedColor.green, selectedColor.blue))
        val x = selectedColor.sizes?.split(",")
        val childCount: Int = binding.cgType.childCount
        for (a in childCount downTo 1) {
            binding.cgType.removeViewAt(a - 1)
        }
        if (x != null) {
            for (types in x) {
                val chip =
                    layoutInflater.inflate(RLayout.chip_layout, binding.cgType, false) as Chip
                chip.text = "$types"
                chip.setOnClickListener {
                    selectedQty = types.split(" ")[0].toFloat()
                    updateView()
                }
                binding.cgType.addView(chip)
            }
        }
    }

    private fun updateView() {
        homeViewModel.getColorantv2(selectedColor, selectedQty)
        observeCalcu()
    }


}