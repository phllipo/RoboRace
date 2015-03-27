package de.otto.roboapp.ui.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.model.Robo;

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

        final String assignedPlayerName = roboAppController.getDataModel().getAssignedPlayer().get(position).getName();
        final String assignedRoboName = roboAppController.getDataModel().getAssignedRobo().get(position).getName();
        String seperatror = " @ ";

        assignedPlayer.setText(assignedPlayerName);
        assignedRobo.setText(assignedRoboName);
        seperatorView.setText(seperatror);
        deselectRobo.setText("x");
        ready.setBackgroundResource(R.drawable.ready_button);
        // ready.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        deselectRobo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roboAppController.deselect();
                roboAppController.getDataModel().deselectPlayerFromRobo(assignedPlayerName, assignedRoboName);
            }
        });

        linearLayout.addView(assignedPlayer);
        linearLayout.addView(seperatorView);
        linearLayout.addView(assignedRobo);
        linearLayout.addView(ready);
        linearLayout.addView(deselectRobo);


        return linearLayout;
    }
}
