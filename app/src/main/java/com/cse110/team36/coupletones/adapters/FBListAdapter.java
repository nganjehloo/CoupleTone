package com.cse110.team36.coupletones.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cse110.team36.coupletones.FaveLocations.FaveLocation;
import com.cse110.team36.coupletones.FaveLocations.SOFaveLoc;
import com.cse110.team36.coupletones.NotifSettings;
import com.cse110.team36.coupletones.R;

import java.util.ArrayList;

/**
 * Created by aspidiske on 5/22/2016.
 */
public class FBListAdapter extends BaseAdapter {
    private ArrayList<SOFaveLoc> list = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;

    public FBListAdapter(ArrayList<SOFaveLoc> list, Context context, FragmentManager fragmentManager, Activity activity) {
        this.list = list;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.so_list_row, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).getName());

        initializeButtons(view, position);

        return view;
    }


    void initializeButtons(View view, final int position) {
        //Handle buttons and add onClickListeners
        ImageButton soundBtn = (ImageButton)view.findViewById(R.id.sound_settings);


        soundBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                activity.finish();
                Intent intent = new Intent(activity, NotifSettings.class);
                intent.putExtra("key",position);
                activity.startActivity(intent);
            }
        });
    }

}