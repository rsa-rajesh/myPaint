package com.bts.mypaint.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bts.mypaint.R
import com.bts.mypaint.domain.dbmodel.TblColors
import java.util.Locale

class ColorsDropdownAdapterV2(context: Context?, items: List<TblColors>?) :
    ArrayAdapter<TblColors?>(
        context!!, 0, items!!
    ) {
    var fullItems: List<TblColors> = arrayListOf()
    override fun getFilter(): Filter {
        return nameFilter
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.item_colors_dropdown, parent, false)
        }
        val lblName = convertView!!.findViewById<TextView>(R.id.tvColorName)
        val lblCode = convertView.findViewById<TextView>(R.id.tvColorCode)
        val card = convertView.findViewById<CardView>(R.id.cvColor)
        val people = getItem(position)
        if (people != null) {
            card.setCardBackgroundColor(Color.argb(255, people.red, people.green, people.blue))
            if (lblName != null) lblName.text = people.shade_name
            if (lblCode != null) {
                assert(lblName != null)
                lblCode.text = people.shad_code
            }
        }
        return convertView
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): CharSequence {
            return (resultValue as TblColors).shade_name + " (" + resultValue.shad_code + ")"
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            val suggestions: MutableList<TblColors> = ArrayList()
            if (constraint != null) {
                if (constraint.isEmpty()) {
                    suggestions.addAll(fullItems)
                } else {
                    val filterPattern =
                        constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                    for (item in fullItems) {
                        if (item.shade_name!!.lowercase(Locale.getDefault())
                                .contains(filterPattern) || item.shad_code!!.lowercase(
                                Locale.getDefault()
                            ).contains(filterPattern)
                        ) {
                            suggestions.add(item)
                        }
                    }
                }
            }
            results.values = suggestions
            results.count = suggestions.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            clear()
            addAll(results.values as List<TblColors>)
            notifyDataSetChanged()
        }
    }

    init {
        fullItems = items?.let { ArrayList(it) }!!
    }
}