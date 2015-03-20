package de.otto.roboapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.model.Robo;

public class RoboRegistrationActivity extends AbstractUpdatableActivity {

    private RoboListAdapter roboListAdapter;
    private PlayerListAdapter playerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robo_registration);

        final RoboAppController roboAppController = (RoboAppController) getApplicationContext();
        String pname = roboAppController.getDataModel().getCurrentPlayerName();

        String welcomeMessage = ((TextView) findViewById(R.id.selectRobo_playerName)).getText().toString();
        String test = welcomeMessage.replace("NAMEPLACEHOLDER", pname);
        ((TextView) findViewById(R.id.selectRobo_playerName)).setText(test);

        final ListView roboSelectList = (ListView) findViewById(R.id.selectRobo_roboList);
        final ListView unassignedPlayer = (ListView) findViewById(R.id.unassignedRoboList);

        roboListAdapter = new RoboListAdapter(this);
        playerListAdapter = new PlayerListAdapter(this);

        roboSelectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Robo clickedRobo = (Robo)roboSelectList.getItemAtPosition(position);
                roboAppController.roboSelected(clickedRobo.getName());
                System.out.println("clicked robo " + clickedRobo.getName());

                //TODO
                if((roboAppController.getDataModel().getPlayerByName(roboAppController.getDataModel().getCurrentPlayerName()).isStatus()) == true){
                    //if alle ready:
                    Intent intent = new Intent(RoboRegistrationActivity.this, SteeringActivity.class);
                    startActivity(intent);
                } else {
                    //Placeholder
                    System.out.println("unready");
                }
           }
        });

        roboSelectList.setAdapter(roboListAdapter);
        unassignedPlayer.setAdapter(playerListAdapter);
    }

    @Override
    public void updateActivity() {
        roboListAdapter.notifyDataSetChanged();
        playerListAdapter.notifyDataSetChanged();
    }
}

