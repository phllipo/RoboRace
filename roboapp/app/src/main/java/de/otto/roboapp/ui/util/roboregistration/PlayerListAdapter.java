package de.otto.roboapp.ui.util.roboregistration;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;

public class PlayerListAdapter extends BaseAdapter {
    private Typeface tf;
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
        tf = Typeface.createFromAsset(activity.getAssets(), "fonts/Munro.ttf");

        LinearLayout linearLayout = new LinearLayout(activity);

        TextView unassignedPlayer = new TextView(activity);

        unassignedPlayer.setTextAppearance(activity, R.style.FonstSmall);

        unassignedPlayer.setTypeface(tf);

        String unassignedPlayerName = roboAppController.getDataModel().getUnassignedPlayer().get(position).getName();
        unassignedPlayer.setText(unassignedPlayerName);

        linearLayout.addView(unassignedPlayer);

        return linearLayout;
    }
}
