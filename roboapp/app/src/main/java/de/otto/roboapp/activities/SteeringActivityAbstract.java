package de.otto.roboapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.model.DataModel;
import de.otto.roboapp.model.RacingData;
import de.otto.roboapp.model.SteeringDirection;

import static de.otto.roboapp.util.ThreadStarter.processInNewThread;


public class SteeringActivityAbstract extends AbstractUpdatableActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steering);

        final RoboAppController roboAppController = (RoboAppController)getApplicationContext();

        final ImageView t_imageViewLeft = (ImageView) findViewById(R.id.ID_Left);
        t_imageViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInNewThread(new Runnable() {
                    @Override
                    public void run() {
                        roboAppController.steer(SteeringDirection.LEFT);
                    }
                });
            }
        });

        final ImageView t_imageViewRight = (ImageView) findViewById(R.id.ID_Left);
        t_imageViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInNewThread(new Runnable() {
                    @Override
                    public void run() {
                        roboAppController.steer(SteeringDirection.RIGHT);
                    }
                });
            }
        });

        displayModel(roboAppController.getDataModel());
    }

    public void displayModel(DataModel dataModel) {
        final TextView t_textViewSpeed = (TextView) findViewById(R.id.ID_Speed);
        RacingData racingData = dataModel.getRacingData();
        int speed = (racingData != null) ? racingData.getCurrentSpeed() : -999;
        t_textViewSpeed.setText(String.valueOf(speed));
    }


    @Override
    public void updateActivity() {
        displayModel(((RoboAppController)getApplicationContext()).getDataModel());
    }
}
