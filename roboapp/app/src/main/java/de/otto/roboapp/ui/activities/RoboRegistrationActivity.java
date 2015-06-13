package de.otto.roboapp.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.ui.util.roboregistration.AssignmentListAdapter;
import de.otto.roboapp.model.Robo;
import de.otto.roboapp.ui.activities.base.AbstractUpdatableActivity;
import de.otto.roboapp.ui.util.roboregistration.PlayerListAdapter;
import de.otto.roboapp.ui.util.roboregistration.RoboListAdapter;

public class RoboRegistrationActivity extends AbstractUpdatableActivity {

    private RoboListAdapter roboListAdapter;
    private PlayerListAdapter playerListAdapter;
    private AssignmentListAdapter assignmentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robo_registration);

        final RoboAppController roboAppController = (RoboAppController) getApplicationContext();
        String playerName = roboAppController.getDataModel().getCurrentPlayerName();

        TextView t_welcome = (TextView) findViewById(R.id.welcomeMessage);
        TextView t_unassinedPlayersHeadline = (TextView) findViewById(R.id.unassignedPlayerHeadline);
        TextView t_unassignedRoboHeadline = (TextView) findViewById(R.id.unassignedRoboHeadline);
        TextView t_assignedPlayersHeadline = (TextView) findViewById(R.id.assignmentListHeadline);

        String welcomeMessage = t_welcome.getText().toString();
        String setPlayerName = welcomeMessage.replace("NAMEPLACEHOLDER", playerName);
        t_welcome.setText(setPlayerName);

        final ListView unassignedPlayers = (ListView) findViewById(R.id.unassignedPlayerList);
        final ListView unassignedRobos = (ListView) findViewById(R.id.unassignedRoboList);
        final ListView assignedPlayers = (ListView) findViewById(R.id.assignmentList);

        //set typeface
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Munro.ttf");
        t_welcome.setTypeface(tf);
        t_unassinedPlayersHeadline.setTypeface(tf);
        t_unassignedRoboHeadline.setTypeface(tf);
        t_assignedPlayersHeadline.setTypeface(tf);

        roboListAdapter = new RoboListAdapter(this);
        playerListAdapter = new PlayerListAdapter(this);
        assignmentListAdapter = new AssignmentListAdapter(this);

        unassignedRobos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Robo clickedRobo = (Robo) unassignedRobos.getItemAtPosition(position);
                roboAppController.roboSelected(clickedRobo.getName());
                System.out.println("clicked robo " + clickedRobo.getName());


            }
        });

        unassignedPlayers.setAdapter(playerListAdapter);
        unassignedRobos.setAdapter(roboListAdapter);
        assignedPlayers.setAdapter(assignmentListAdapter);


    }


    @Override
    public void updateActivity() {
        roboListAdapter.notifyDataSetChanged();
        playerListAdapter.notifyDataSetChanged();
        assignmentListAdapter.notifyDataSetChanged();
    }
}

