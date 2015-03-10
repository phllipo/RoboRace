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


public class SteeringActivity extends AbstractUpdatableActivity {


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
                        System.out.println("clicked left");
                        roboAppController.steer(SteeringDirection.LEFT);
                    }
                });
            }
        });

        final ImageView t_imageViewRight = (ImageView) findViewById(R.id.ID_Rigth);
        t_imageViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInNewThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("clicked right");
                        roboAppController.steer(SteeringDirection.RIGHT);
                    }
                });
            }
        });

        final ImageView t_imageViewA = (ImageView) findViewById(R.id.ID_Button_A);
        t_imageViewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInNewThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("clicked a");
                        roboAppController.steer(SteeringDirection.FORWARD);
                    }
                });
            }
        });

        final ImageView t_imageViewB = (ImageView) findViewById(R.id.ID_Button_B);
        t_imageViewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInNewThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("clicked b");
                        roboAppController.steer(SteeringDirection.BACKWARD);
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
