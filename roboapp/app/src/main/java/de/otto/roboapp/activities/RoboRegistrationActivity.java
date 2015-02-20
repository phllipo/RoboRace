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
import de.otto.roboapp.model.Player;
import de.otto.roboapp.model.Robo;

public class RoboRegistrationActivity extends AbstractUpdatableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robo_registration);


        final RoboAppController roboAppController = (RoboAppController) getApplicationContext();
        final ListView selectRoboList = (ListView) findViewById(R.id.selectRobo_roboList);
        //TODO
        // Weitere ListView oder GridView erstellen, um die Map(Elemente) nicht clickable zu machen


        selectRoboList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                roboAppController.assignPlayerToRobo(selectRoboList.getItemAtPosition(position).toString());


            }
        });
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        for (Robo robo : roboAppController.getDataModel().getRoboList()) {
            adapter.add(robo.getName());
        }
        for (HashMap.Entry<Player, Robo> map :
                roboAppController.getDataModel().getPlayerToRoboAssignmentMap().entrySet()) {
            adapter.add(map.getKey().getName() + " : " + map.getValue().getName());
        }
        /* Schleife um Map Werte von RoboList Werten zu unterscheiden und diese ggf. notClickable zu machen
        eine andere Alternative ist es eine weitere ListView zu erzeugen und die Map in dieser abzuspeichern.
        Um diese eigenständig nicht clickable zu machen
         */
       /* for(int i = 0; i < adapter.getCount(); i++) {
            Object obj = adapter.getItem(i);

            for (HashMap.Entry<Player, Robo> map : roboAppController.getDataModel().getPlayerToRoboAssignmentMap().entrySet()) {
                if (obj == (map.getKey().getName() + " : " +  map.getValue().getName())) {
                    selectRoboList.setClickable(false);
                }


            }
      */
        selectRoboList.setAdapter(adapter);
    }

    @Override
    public void updateActivity() {

    }
}

