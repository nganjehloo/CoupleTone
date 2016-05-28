package com.cse110.team36.coupletones.lists.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cse110.team36.coupletones.FaveLocations.FaveLocation;
import com.cse110.team36.coupletones.FireBase.FireBaseManager;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.Dialogs.LocationDialog;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.R;

import java.util.ArrayList;

/**
 * MyCustomAdapter adapters the buttons for the ListView
 *     in such a way that button
 */

public class MyCustomAdapter extends BaseAdapter {
    private ArrayList<FaveLocation> list = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;

    public MyCustomAdapter() {}

    public MyCustomAdapter(Activity activity, ArrayList<FaveLocation> list, Context context, FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    public void setList(ArrayList<FaveLocation> l) {
        list = l;
    }

    public void setContext(Context c) {
        context = c;
    }

    public void setFragmentManager(FragmentManager fm) {
        fragmentManager = fm;
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
            view = inflater.inflate(R.layout.custom_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).getName());

        initializeButtons(view, position);

        return view;
    }

    void initializeButtons(View view, final int position) {
        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button renameBtn = (Button)view.findViewById(R.id.rename_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                String name = FaveLocationManager.locList.get(position).getName();
                FaveLocationManager.removeLocation(FaveLocationManager.locList.get(position).getName());
                notifyDataSetChanged();

                //TODO: ADD UNIQUE ID
                //Remove from Firebase
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                FireBaseManager FBman = new FireBaseManager(sharedPreferences);

                LocationFB locFB = new LocationFB();
                locFB.setName(name);
                FBman.remove(locFB);
            }
        });

        renameBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LocationDialog locationDialog = new LocationDialog();
                locationDialog.setPosition(position);
                locationDialog.show(fragmentManager, "rename loc");
            }
        });
    }
}