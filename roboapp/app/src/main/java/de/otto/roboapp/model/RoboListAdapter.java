package de.otto.roboapp.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.activities.RoboRegistrationActivity;
import de.otto.roboapp.activities.UpdatableActivity;

/**
 * Created by ariga on 04.03.15.
 */
public class RoboListAdapter extends BaseAdapter {
    private Activity activity;
    private RoboAppController roboAppController;

    public RoboListAdapter(Activity activity) {
        this.activity = activity;
        roboAppController  = (RoboAppController) activity.getApplicationContext();

    }

    @Override
    public int getCount() {
        return  roboAppController.getDataModel().getRoboList().size();
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


        TextView textViewSpieler = new TextView(activity);
        TextView textViewRobo = new TextView(activity);

        String nameDesRobo = roboAppController.getDataModel().getRoboList().get(position).getName();
        //String nameDesSpielers = roboAppController.getDataModel().getPlayerList().get(position).getName();
        textViewRobo.setText(nameDesRobo);
        textViewSpieler.setText("test");

        linearLayout.addView(textViewRobo);
        linearLayout.addView(textViewSpieler);


        return linearLayout;

    }
}
