package com.cse110.team36.coupletones.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cse110.team36.coupletones.FaveLocations.FaveLocation;
import com.cse110.team36.coupletones.R;

import java.util.ArrayList;

/**
 * Created by Andrew on 5/21/2016.
 */
public class SOVisitedAdapter extends MyCustomAdapter {

    private ArrayList<FaveLocation> list = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;

    public SOVisitedAdapter(ArrayList<FaveLocation> list, Context context, FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).getName());

        initializeButtons(view, position);

        return view;
    }

}
