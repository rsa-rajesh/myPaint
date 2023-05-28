package com.bts.mypaint.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bts.mypaint.R;
import com.bts.mypaint.domain.dbmodel.TblColors;

import java.util.ArrayList;
import java.util.List;

public class ColorsDropdownAdapterV2 extends ArrayAdapter<TblColors> {

    List<TblColors> fullItems;

    public ColorsDropdownAdapterV2(Context context, List<TblColors> items) {
        super(context, 0, items);
        fullItems = new ArrayList<TblColors>(items);
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_colors_dropdown, parent, false);
        }

        TextView lblName = convertView.findViewById(R.id.tvColorName);
        TextView lblCode = convertView.findViewById(R.id.tvColorCode);
        CardView card = convertView.findViewById(R.id.cvColor);

        TblColors people = getItem(position);
        if (people != null) {
            card.setCardBackgroundColor(Color.argb(255, people.getRed(), people.getGreen(), people.getBlue()));

            if (lblName != null)
                lblName.setText(people.getShade_name());

            if (lblCode != null) {
                assert lblName != null;
                lblCode.setText(people.getShad_code());
            }
        }
        return convertView;
    }


    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((TblColors) resultValue).getShade_name() + " (" + ((TblColors) resultValue).getShad_code() + ")";
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {


            FilterResults results = new FilterResults();
            List<TblColors> suggestions= new ArrayList<>();

            if (constraint == null || constraint.length()==0) {
                suggestions.addAll(fullItems);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TblColors item :fullItems ) {
                    if (item.getShade_name().toLowerCase().contains(filterPattern) || item.getShad_code().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
