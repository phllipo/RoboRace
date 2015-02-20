package de.otto.roboapp.activities;

import android.app.Activity;

public abstract class AbstractUpdatableActivity extends Activity implements UpdatableActivity {

    @Override
    protected void onStart() {
        super.onStart();
        ActivityMontitor app = (ActivityMontitor)getApplication();
        app.setActiveActivity(this);
    }

    @Override
    protected void onStop() {
        ActivityMontitor app = (ActivityMontitor)getApplication();
        app.setActiveActivity(null);
        super.onStop();
    }

    public final void updateActivityFromBgThread() {
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                updateActivity();
            }});
    }
}
