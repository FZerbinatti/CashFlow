package com.dreamsphere.cashflow.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dreamsphere.cashflow.Models.Cathegories;
import com.dreamsphere.cashflow.R;

import java.util.ArrayList;

public class GridviewAdapter extends ArrayAdapter {


    public GridviewAdapter(@NonNull Context context, ArrayList<Cathegories> cathegories) {
        super(context, 0,cathegories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        HolderView holderView;

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.grid_view_item, parent, false);

            holderView = new HolderView(convertView);
            convertView.setTag(holderView);
        }else {
            holderView = (HolderView) convertView.getTag();
        }

        Cathegories cathegories = (Cathegories) getItem(position);
        holderView.icon.setImageResource(cathegories.getIconID());
        holderView.textView.setText(cathegories.getIconText());


        return convertView;
    }

    private static class HolderView{
        private final ImageView icon;
        private final TextView textView;

        public HolderView(View view){
            icon = view.findViewById(R.id.icon_image);
            textView = view.findViewById(R.id.icon_text);
        }
    }

}
