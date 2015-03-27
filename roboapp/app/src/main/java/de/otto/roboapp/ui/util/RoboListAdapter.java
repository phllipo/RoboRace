package de.otto.roboapp.ui.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.otto.roboapp.RoboAppController;

public class RoboListAdapter extends BaseAdapter {
    private Activity activity;
    private RoboAppController roboAppController;

    public RoboListAdapter(Activity activity) {
        this.activity = activity;
        roboAppController  = (RoboAppController) activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return  roboAppController.getDataModel().getUnassignedRobo().size();
    }

    @Override
    public Object getItem(int position) {
        return roboAppController.getDataModel().getRoboList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout linearLayout = new LinearLayout(activity);

        TextView unassignedRobo = new TextView(activity);

        String unassignedRoboName = roboAppController.getDataModel().getUnassignedRobo().get(position).getName();
        unassignedRobo.setText(unassignedRoboName);

        linearLayout.addView(unassignedRobo);

        return linearLayout;
    }
}
