package de.otto.roboapp.activities;

import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.model.PlayerListAdapter;
import de.otto.roboapp.model.RoboListAdapter;
import de.otto.roboapp.model.Player;
import de.otto.roboapp.model.Robo;

public class RoboRegistrationActivity extends AbstractUpdatableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robo_registration);
          /* TODO
             unassignedPlayer Liste wird noch nicht angezeigt
         */

        final RoboAppController roboAppController = (RoboAppController) getApplicationContext();
        final ListView roboSelectList = (ListView) findViewById(R.id.selectRobo_roboList);
        final ListView unassignedPlayer = (ListView) findViewById(R.id.unassignedRoboList);
        RoboListAdapter roboListAdapter = new RoboListAdapter(this);
        PlayerListAdapter playerListAdapter = new PlayerListAdapter(this);

        roboSelectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        roboAppController.sendLocalPlayerToRoboAssignment(roboSelectList.getItemAtPosition(position).toString());
            }

        });

        roboSelectList.setAdapter(roboListAdapter);
        unassignedPlayer.setAdapter(playerListAdapter);
    }

    @Override
    public void updateActivity() {

    }
}

