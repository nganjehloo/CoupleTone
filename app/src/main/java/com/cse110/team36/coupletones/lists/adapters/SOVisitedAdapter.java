package com.cse110.team36.coupletones.lists.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cse110.team36.coupletones.FaveLocations.FaveLocation;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.R;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Andrew on 5/21/2016.
 */
public class SOVisitedAdapter extends MyCustomAdapter {

    private PriorityQueue<LocationFB> list;
    private Context context;
    private FragmentManager fragmentManager;

    public SOVisitedAdapter(PriorityQueue<LocationFB> list, Context context, FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.visited_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.VisitedName);
        TextView listItemTime = (TextView)view.findViewById(R.id.VisitedTime);
        listItemText.setText(list.peek().getName());
        listItemTime.setText(list.peek().getTime());
        list.remove();

        initializeButtons(view, position);

        return view;
    }

}
