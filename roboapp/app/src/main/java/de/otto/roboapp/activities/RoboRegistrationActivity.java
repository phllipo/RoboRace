package de.otto.roboapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.model.Robo;
import de.otto.roboapp.R;

public class RoboRegistrationActivity  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_robo);

        final RoboAppController roboAppController = (RoboAppController)getApplicationContext();

        ListView selectRoboList = (ListView) findViewById(R.id.selectRobo_roboList);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,new ArrayList<String>());
        for(Robo robo : roboAppController.getDataModel().getRoboList()) {
            adapter.add(robo.getName());
        }
        selectRoboList.setAdapter(adapter);


    }

}
