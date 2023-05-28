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
import java.util.Objects;

public class ColorsDropdownAdapter extends ArrayAdapter<TblColors> {

    Context context;
    int resource;
    List<TblColors> items, tempItems, suggestions;

    public ColorsDropdownAdapter(Context context, int resource, List<TblColors> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<TblColors>(items); // this makes the difference.
        suggestions = new ArrayList<TblColors>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_colors_dropdown, parent, false);
        }
        TblColors people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.tvColorName);
            TextView lblCode = (TextView) view.findViewById(R.id.tvColorCode);
            CardView card =  view.findViewById(R.id.cvColor);

            card.setCardBackgroundColor(Color.argb(255,people.getRed(),people.getGreen(),people.getBlue()));

            if (lblName != null)
                lblName.setText(people.getShade_name());

            if (lblCode != null) {
                assert lblName != null;
                lblCode.setText(people.getShad_code());
            }
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((TblColors) resultValue).getShade_name();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (TblColors people : tempItems) {
                    if (people.getShade_name().toLowerCase().contains(constraint.toString().toLowerCase()) || Objects.requireNonNull(people.getShad_code()).toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
//            try{
//                List<TblColors> filterList = (ArrayList<TblColors>) results.values;
//                if (results.count > 0) {
//                    clear();
//                    for (TblColors people : filterList) {
//                        add(people);
//                    }
//                    notifyDataSetChanged();
//                }
//            }catch (Exception e){}

        }
    };
}
