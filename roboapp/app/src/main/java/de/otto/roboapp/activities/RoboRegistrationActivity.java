package de.otto.roboapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.model.PlayerListAdapter;
import de.otto.roboapp.model.Robo;
import de.otto.roboapp.model.RoboListAdapter;

public class RoboRegistrationActivity extends AbstractUpdatableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robo_registration);
        final RoboAppController roboAppController = (RoboAppController) getApplicationContext();

        final ListView roboSelectList = (ListView) findViewById(R.id.selectRobo_roboList);
        String welcomeMessage = getResources().getString(R.string.welcome);
        welcomeMessage.replace("NAMEPLACEHOLDER", "test");
        ((TextView) findViewById(R.id.selectRobo_playerName)).setText(welcomeMessage);

        final ListView unassignedPlayer = (ListView) findViewById(R.id.unassignedRoboList);
        RoboListAdapter roboListAdapter = new RoboListAdapter(this);
        PlayerListAdapter playerListAdapter = new PlayerListAdapter(this);

        roboSelectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Robo clickedRobo = (Robo)roboSelectList.getItemAtPosition(position);
                roboAppController.roboSelected(clickedRobo.getName());
                System.out.println("clicked robo " + clickedRobo.getName());

                Intent intent = new Intent(RoboRegistrationActivity.this, SteeringActivity.class);
                startActivity(intent);

           }

        });

        roboSelectList.setAdapter(roboListAdapter);
        unassignedPlayer.setAdapter(playerListAdapter);
    }

    @Override
    public void updateActivity() {

    }
}

