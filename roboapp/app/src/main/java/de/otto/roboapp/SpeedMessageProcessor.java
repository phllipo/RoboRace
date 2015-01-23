package de.otto.roboapp;

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luca on 23.01.15.
 */
public class SpeedMessageProcessor extends MessageProcessor {
    private final TextView speedField;

    public SpeedMessageProcessor(TextView speedField) {

        this.speedField = speedField;
    }

    @Override
    public String processMessage(JSONObject json) throws JSONException {
        String v = "SPEEEEEEEEEEEEEEEEEEEEEEED: " + json.getJSONObject("data").getString("speed");
        return v;
    }

    @Override
    public void refreshUI(Object processData) {
        speedField.setText((String) processData);
    }

}
