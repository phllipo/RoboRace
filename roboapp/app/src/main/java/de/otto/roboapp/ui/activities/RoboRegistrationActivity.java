package de.otto.roboapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.model.Player;
import de.otto.roboapp.model.Robo;
import de.otto.roboapp.ui.activities.base.AbstractUpdatableActivity;
import de.otto.roboapp.ui.util.PlayerListAdapter;
import de.otto.roboapp.ui.util.RoboListAdapter;

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


        String welcomeMessage = ((TextView) findViewById(R.id.welcomeMessage)).getText().toString();
        String setPlayerName = welcomeMessage.replace("NAMEPLACEHOLDER", playerName);
        ((TextView) findViewById(R.id.welcomeMessage)).setText(setPlayerName);

        final ListView unassignedPlayers = (ListView) findViewById(R.id.unassignedPlayerList);
        final ListView unassignedRobos = (ListView) findViewById(R.id.unassignedRoboList);
        final ListView assignedPlayers = (ListView) findViewById(R.id.assignmentList);

        roboListAdapter = new RoboListAdapter(this);
        playerListAdapter = new PlayerListAdapter(this);
        assignmentListAdapter = new AssignmentListAdapter(this);

/*        assignedPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player clickedPlayer = (Player) assignedPlayers.getItemAtPosition(position);
                roboAppController.
            }
        });*/

        unassignedRobos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Robo clickedRobo = (Robo) unassignedRobos.getItemAtPosition(position);
                roboAppController.roboSelected(clickedRobo.getName());
                System.out.println("clicked robo " + clickedRobo.getName());

                //TODO
                //if((roboAppController.getDataModel().getPlayerByName(roboAppController.getDataModel().getCurrentPlayerName()).isStatus()) == true){

                    //if alle ready:
                    Intent intent = new Intent(RoboRegistrationActivity.this, SteeringActivity.class);
                    startActivity(intent);
                /*} else {
                    //Placeholder
                    System.out.println("unready");
                }*/
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

