package de.otto.roboapp.ui.util.result;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.otto.roboapp.RoboAppController;

/**
 * Created by luca on 18.05.15.
 */
public class PlacementListAdapter extends BaseAdapter {
    private Activity activity;
    private RoboAppController roboAppController;

    public PlacementListAdapter(Activity activity) {
        this.activity = activity;
        roboAppController = (RoboAppController) activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return roboAppController.getDataModel().getFinishedPlayerList().size();
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
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);


        TextView placementView = new TextView(activity);
        TextView playerNameView = new TextView(activity);
        TextView timeView = new TextView(activity);

        LinearLayout.LayoutParams layoutParamsWeight2 =  new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, (float) 2.0);
        LinearLayout.LayoutParams layoutParamsWeight5 =  new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, (float) 5.0);

        layoutParamsWeight2.setMargins(60,0,0,0);
        layoutParamsWeight5.setMargins(60,0,0,0);

        placementView.setLayoutParams(layoutParamsWeight2);
        playerNameView.setLayoutParams(layoutParamsWeight5);
        timeView.setLayoutParams(layoutParamsWeight5);

        String placement = Integer.toString(position + 1);
        String playerName = roboAppController.getDataModel().getOrderedResultsList().get(position).getName();
        String time = Integer.toString(roboAppController.getDataModel().getOrderedResultsList().get(position).getResultTime());

        placementView.setText(placement + ".");
        playerNameView.setText(playerName);
        timeView.setText(time + "s");

        linearLayout.addView(placementView);
        linearLayout.addView(playerNameView);
        linearLayout.addView(timeView);
        return linearLayout;
    }
}
