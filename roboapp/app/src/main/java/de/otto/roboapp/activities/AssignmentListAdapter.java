package de.otto.roboapp.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;

/**
 * Created by samaeder on 23.03.15.
 */
public class AssignmentListAdapter extends BaseAdapter {
    private Activity activity;
    private RoboAppController roboAppController;

    public AssignmentListAdapter(Activity activity) {
        this.activity = activity;
        roboAppController  = (RoboAppController) activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return roboAppController.getDataModel().getAssignedPlayer().size();
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
        ImageView profil = new ImageView(activity);
        TextView assignedPlayer = new TextView(activity);
        TextView assignedRobo = new TextView(activity);
        TextView seperatorView = new TextView(activity);
        Button deselectRobo = new Button(activity);
        Button ready = new Button(activity);

        //ready.setBackground(ready_button);

        String assignedPlayerName = roboAppController.getDataModel().getAssignedPlayer().get(position).getName();
        String assignedRoboName = roboAppController.getDataModel().getAssignedRobo().get(position).getName();
        String seperatror = " @ ";

        assignedPlayer.setText(assignedPlayerName);
        assignedRobo.setText(assignedRoboName);
        seperatorView.setText(seperatror);
        deselectRobo.setText("x");
        ready.setBackgroundColor(Color.GREEN);

        linearLayout.addView(assignedPlayer);
        linearLayout.addView(seperatorView);
        linearLayout.addView(assignedRobo);
        linearLayout.addView(ready);
        linearLayout.addView(deselectRobo);


        return linearLayout;
    }
}
