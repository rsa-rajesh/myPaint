package com.bts.mypaint.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidcommon.base.ImmutableRecyclerAdapter
import androidcommon.base.VBViewHolder
import com.bts.mypaint.data.Prefs
import com.bts.mypaint.databinding.ItemFandeckBinding
import com.bts.mypaint.domain.dbmodel.TblColors
import kotlin.properties.Delegates

class FandeckListAdapter(
    private val onItemClick: (item: TblColors) -> Unit = {},
    private val pref: Prefs
) :
    ImmutableRecyclerAdapter<TblColors, ItemFandeckBinding>() {
    override var items: List<TblColors> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getViewBinding(parent: ViewGroup) = ItemFandeckBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VBViewHolder<ItemFandeckBinding>,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {

            name.text=item.card_name
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}