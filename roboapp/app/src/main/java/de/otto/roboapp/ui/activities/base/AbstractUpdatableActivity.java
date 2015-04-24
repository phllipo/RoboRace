package de.otto.roboapp.ui.activities.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public abstract class AbstractUpdatableActivity extends Activity implements UpdatableActivity {
    private Boolean exit = false;

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


    @Override
    public void onBackPressed() {
        if (exit) {
            finishAffinity(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
