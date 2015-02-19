package de.otto.roboappOld;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luca on 23.01.15.
 */
public abstract class MessageProcessor<T> {
    protected abstract T processMessage(JSONObject json) throws JSONException;

    protected abstract void refreshUI(T processData);

    public void process(JSONObject json, Activity act) throws JSONException {
        final T obj = processMessage(json);
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshUI(obj);
            }
        });
    }
}
