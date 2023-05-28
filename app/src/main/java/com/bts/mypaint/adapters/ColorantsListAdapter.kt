package com.bts.mypaint.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidcommon.base.ImmutableRecyclerAdapter
import androidcommon.base.VBViewHolder
import com.bts.mypaint.data.Prefs
import com.bts.mypaint.databinding.ItemColorantsBinding
import com.bts.mypaint.domain.dbmodel.TblCalculation
import kotlin.properties.Delegates

class ColorantsListAdapter(
    private val pref: Prefs
) :
    ImmutableRecyclerAdapter<TblCalculation, ItemColorantsBinding>() {
    override var items: List<TblCalculation> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getViewBinding(parent: ViewGroup) = ItemColorantsBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VBViewHolder<ItemColorantsBinding>,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {
            name.text=item.tblColorants!!.colorant_name
            code.text= item.tblColorants.colorant_code
            qty.text = String.format("%.3f", item.qnt)+" ML"
            price.text = "Rs. "+String.format("%.3f",item.qnt * (item.tblColorants.unit_price/1000)) +" /-"
            progress.setFrontWaveColor(Color.argb(255,item.tblColorants.red,item.tblColorants.green,item.tblColorants.blue))
            progress.setBehindWaveColor(Color.argb(50,item.tblColorants.red,item.tblColorants.green,item.tblColorants.blue))
        }
    }
}