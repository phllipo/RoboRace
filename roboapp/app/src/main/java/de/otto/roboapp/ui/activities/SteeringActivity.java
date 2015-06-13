package de.otto.roboapp.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.model.DataModel;
import de.otto.roboapp.model.RacingData;
import de.otto.roboapp.model.RacingState;
import de.otto.roboapp.model.SteeringDirection;
import de.otto.roboapp.ui.activities.base.AbstractUpdatableActivity;
import de.otto.roboapp.ui.components.SpeedLights;

import static de.otto.roboapp.util.ThreadStarter.processInNewThread;


public class SteeringActivity extends AbstractUpdatableActivity {

    TextView speedText;
    ArrayList <ImageView> imageViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final RoboAppController roboAppController = (RoboAppController)getApplicationContext();

        // remove title bar, set fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_steering);

        SeekBar speedSlider = (SeekBar) findViewById(R.id.ID_Speed_Slider);


        speedSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                processInNewThread(new Runnable() {
                    @Override
                    public void run() {
                        long targetSpeed = Math.round((progress - 20) * 1.25); //-25 <-> +100
                        System.out.println("slided to " + targetSpeed);
                        roboAppController.steer(targetSpeed);
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final ImageView t_imageViewLeft = (ImageView) findViewById(R.id.ID_Left);
        t_imageViewLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getX() >= 0 && event.getY() >= 0 && event.getX() < v.getWidth() && event.getY() < v.getHeight()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        processInNewThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("pressed left");
                                roboAppController.steer(SteeringDirection.LEFT);
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        processInNewThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("released left");
                                roboAppController.steer(SteeringDirection.NONE);
                            }
                        });
                    }
                }
                return true;
            }
        });

        final ImageView t_imageViewRight = (ImageView) findViewById(R.id.ID_Rigth);
        t_imageViewRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getX() >= 0 && event.getY() >= 0 && event.getX() < v.getWidth() && event.getY() < v.getHeight()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        processInNewThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("pressed right");
                                roboAppController.steer(SteeringDirection.RIGHT);
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        processInNewThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("released right");
                                roboAppController.steer(SteeringDirection.NONE);
                            }
                        });
                    }
                }

                return true;
            }
        });

        displayModel(roboAppController.getDataModel());
    }

    public void displayModel(DataModel dataModel) {

        switch (dataModel.getRacingData().getRacingState()) {
            case COUNTDOWN_RUNNING: {
                final TextView t_textViewTimer = (TextView) findViewById(R.id.CountdownTimer);

                Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Munro.ttf");
                t_textViewTimer.setTypeface(tf);

                new CountDownTimer(3000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        t_textViewTimer.setText(String.valueOf((millisUntilFinished+500)/1000));
                        // String.valueOf(dataModel.getRacingData().getCountdownRemainingTime())
                    }

                    public void onFinish() {
                        t_textViewTimer.setText("GO!");
                    }
                }.start();
                break;
            }
            case STARTED: {
                final TextView t_textViewTimer = (TextView) findViewById(R.id.CountdownTimer);
                t_textViewTimer.setVisibility(View.INVISIBLE);
                RacingData racingData = dataModel.getRacingData();
                int speed = (racingData != null) ? racingData.getCurrentSpeed() : -999;
                final SpeedLights speedLightsForward = (SpeedLights) findViewById(R.id.ID_Speed_Lights_Forward);
                final SpeedLights speedLightsBackward = (SpeedLights) findViewById(R.id.ID_Speed_Lights_Backward);
                if(speed > 0 ) {
                    speedLightsForward.setSpeed(speed);
                    speedLightsBackward.setSpeed(0);
                } else {
                    speedLightsForward.setSpeed(0);
                    speedLightsBackward.setSpeed(-speed * 3);
                }
                break;
            }
        }
    }

    @Override
    public void updateActivity() {
        DataModel dataModel = ((RoboAppController)getApplicationContext()).getDataModel();
        displayModel(dataModel);
        if(dataModel.getRacingData().getRacingState().equals(RacingState.FINISHED)) {
            switchActivity(ResultsActivity.class);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //CF: prüfen, ob in Konflikt mit dem erfolgreichen abschluss des Rennens.
        ((RoboAppController)getApplicationContext()).deselect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final RoboAppController roboAppController = (RoboAppController)getApplicationContext();
        if(roboAppController.getDataModel().getMyAssignedRobo() == null) {
            switchActivity(RoboRegistrationActivity.class);
        }
    }
}
