package com.cse110.team36.coupletones;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
/**
 * Created by admin on 5/7/16.
 */
//public class MyCustomAdapter {
//}
public class MyCustomAdapter extends BaseAdapter {
    private ArrayList<FaveLocation> list = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;

    public MyCustomAdapter(ArrayList<FaveLocation> list, Context context, FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.fragmentManager = fragmentManager;
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
                FaveLocationManager.removeLocation(FaveLocationManager.locList.get(position).getName());
                notifyDataSetChanged();
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