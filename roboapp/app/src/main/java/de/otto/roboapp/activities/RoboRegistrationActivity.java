package de.otto.roboapp.activities;

import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.model.Player;
import de.otto.roboapp.model.Robo;

public class RoboRegistrationActivity extends AbstractUpdatableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robo_registration);
          /* TODO
         
            roboPlayerListAdapter wird noch nicht angezeigt, Layout Problematik.
         */

        final RoboAppController roboAppController = (RoboAppController) getApplicationContext();
        final ListView roboSelectList = (ListView) findViewById(R.id.selectRobo_roboList);
        final ListView  roboPlayerMapList = (ListView) findViewById(R.id.roboRegistration_mapList);

        roboSelectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                roboAppController.sendLocalPlayerToRoboAssignment(roboSelectList.getItemAtPosition(position).toString());


            }

        });
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        for (Robo robo : roboAppController.getDataModel().getRoboList()) {
            adapter.add(robo.getName());
        }
        ArrayAdapter<String> mapArrayAdapter;
        mapArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        for (HashMap.Entry<Player, Robo> map :
                roboAppController.getDataModel().getPlayerToRoboAssignmentMap().entrySet()) {
            mapArrayAdapter.add(map.getKey().getName() + " : " + map.getValue().getName());
        }

       /* for(int i = 0; i < adapter.getCount(); i++) {
            Object obj = adapter.getItem(i);

            for (HashMap.Entry<Player, Robo> map : roboAppController.getDataModel().getPlayerToRoboAssignmentMap().entrySet()) {
                if (obj == (map.getKey().getName() + " : " +  map.getValue().getName())) {
                    selectRoboList.setClickable(false);
                }


            }
      */
        roboSelectList.setAdapter(adapter);
        roboPlayerMapList.setAdapter(mapArrayAdapter);
    }

    @Override
    public void updateActivity() {

    }
}

