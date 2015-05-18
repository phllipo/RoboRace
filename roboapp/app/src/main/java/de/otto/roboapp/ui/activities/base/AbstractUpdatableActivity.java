package de.otto.roboapp.ui.activities.base;

import android.app.Activity;
import android.content.Intent;

public abstract class AbstractUpdatableActivity extends Activity implements UpdatableActivity {

    @Override
    protected void onStart() {
        super.onStart();
        ActivityMontitor app = (ActivityMontitor)getApplication();
        app.setActiveActivity(this);
        System.out.println("onStart " + this.getLocalClassName());
    }

    @Override
    protected void onStop() {
        ActivityMontitor app = (ActivityMontitor)getApplication();
        if(app.getActiveActivity() != null && app.getActiveActivity().equals(this)) {
            app.setActiveActivity(null);
        }
        super.onStop();
        System.out.println("onStop " + this.getLocalClassName());
    }

    public final void updateActivityFromBgThread() {
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                updateActivity();
            }});
    }

    @Override
    public void switchActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }



}
