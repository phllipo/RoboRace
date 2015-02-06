package de.otto.roboapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;


public class SelectRobo extends I_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_robo);

        TextView t = (TextView) findViewById(R.id
                .selectRobo_playerName);
        ListView selectRoboList = (ListView) findViewById(R.id.selectRobo_roboList);

        //TODO
        sc.setSelectRoboList(selectRoboList);
        ArrayAdapter<String> adapter;
        ArrayList<String> test = new ArrayList<String>();
        test.add("test1");
        test.add("test2");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, test);
        selectRoboList.setAdapter(adapter);

        sc.sendMsg("gib mir Robos!");


        // Übergebene Objekte erhalten
        /*Intent intent = getIntent();
        if(intent != null) {
            //String playerName = intent.getExtras().getString("playerName");
            //t.setText(playerName);
            //ServerController sc = (ServerController) intent.getParcelableExtra("serverController");
            // sc = (ServerController) intent.getExtras().getSerializable("serverController");


        }*/

    }

}
