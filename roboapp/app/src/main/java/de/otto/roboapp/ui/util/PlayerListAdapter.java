package de.otto.roboapp.ui.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.otto.roboapp.RoboAppController;

public class PlayerListAdapter extends BaseAdapter {
    private Activity activity;
    private RoboAppController roboAppController;

    public PlayerListAdapter(Activity activity) {
        this.activity = activity;
        roboAppController  = (RoboAppController) activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return  roboAppController.getDataModel().getUnassignedPlayer().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout linearLayout = new LinearLayout(activity);

        TextView unassignedPlayer = new TextView(activity);

        String unassignedPlayerName = roboAppController.getDataModel().getUnassignedPlayer().get(position).getName();
        unassignedPlayer.setText(unassignedPlayerName);

        linearLayout.addView(unassignedPlayer);

        return linearLayout;
    }
}
