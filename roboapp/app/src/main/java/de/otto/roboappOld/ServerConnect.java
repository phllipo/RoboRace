package de.otto.roboappOld;

import android.os.AsyncTask;

import java.util.Arrays;

/**
 * Created by luca on 23.01.15.
 * Diese Klasse wird zur Zeit, in der App nicht verwendet, wofür wird sie benötigt ?
 */
public class ServerConnect extends AsyncTask<String, Void, Void> {



    private MainActivity mainActivity;

    public ServerConnect(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    @Override
    protected Void doInBackground(String... params) {
        System.out.println("pre click");
        System.out.println(Arrays.asList(params));
        ServerController sc = new ServerController(params[0], params[1], params[2]);
        System.out.println("post click");
        mainActivity.sc = sc;
        return null;
    }
}
