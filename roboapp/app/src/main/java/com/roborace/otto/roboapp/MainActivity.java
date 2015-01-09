package com.roborace.otto.roboapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URISyntaxException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webSocketAppClient client = new webSocketAppClient();
        try {
            client.createConnectionToServer();
        } catch (URISyntaxException e) {
            System.out.println("Error while connecting: " + e);
        }
    }



}
