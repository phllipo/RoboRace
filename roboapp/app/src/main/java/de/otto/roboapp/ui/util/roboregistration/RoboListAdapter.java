package de.otto.roboapp.ui.util.roboregistration;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.otto.roboapp.RoboAppController;

public class RoboListAdapter extends BaseAdapter {
    private Typeface tf;
    private Activity activity;
    private RoboAppController roboAppController;

    public RoboListAdapter(Activity activity) {
        this.activity = activity;
        roboAppController  = (RoboAppController) activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return  roboAppController.getDataModel().getUnassignedRobos().size();
    }

    @Override
    public Object getItem(int position) {
        return roboAppController.getDataModel().getUnassignedRobos  ().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tf = Typeface.createFromAsset(activity.getAssets(), "fonts/Munro.ttf");

        LinearLayout linearLayout = new LinearLayout(activity);

        TextView unassignedRobo = new TextView(activity);

        unassignedRobo.setTypeface(tf);

        String unassignedRoboName = roboAppController.getDataModel().getUnassignedRobos().get(position).getName();
        unassignedRobo.setText(unassignedRoboName);

        linearLayout.addView(unassignedRobo);

        return linearLayout;
    }
}
