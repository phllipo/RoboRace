package de.otto.roboapp.ui.activities.base;

import android.app.Activity;

import de.otto.roboapp.ui.activities.SteeringActivity;

public interface UpdatableActivity {

    public void updateActivity();

    public void updateActivityFromBgThread();

    public void switchActivity(Class<? extends Activity> steeringActivityClass);
}
