package com.bts.mypaint.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidcommon.RDrawable
import androidcommon.base.ImmutableRecyclerAdapter
import androidcommon.base.VBViewHolder
import androidcommon.extension.load
import com.bts.mypaint.R
import com.bts.mypaint.data.Prefs
import com.bts.mypaint.databinding.ItemProductsBinding
import com.bts.mypaint.domain.dbmodel.TblColors
import com.bumptech.glide.Glide
import kotlin.properties.Delegates

class ProductListAdapter(
    private val onItemClick: (item: TblColors) -> Unit = {},
    private val pref: Prefs
) :
    ImmutableRecyclerAdapter<TblColors, ItemProductsBinding>() {
    override var items: List<TblColors> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getViewBinding(parent: ViewGroup) = ItemProductsBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VBViewHolder<ItemProductsBinding>,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {
            Glide.with(image.context).load(item.image).into(image)
            image.load(item.image)
            name.text=item.product_name

            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}