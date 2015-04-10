package de.otto.roboapp.model;

import java.util.Date;

public class RacingData {
    final static long COUNTDOWN_TIME = 5000;
    private int currentSpeed;
    private Gadget ActiveGadget;
    private RacingState racingState = RacingState.NOT_STARTED;
    private long countdownStartTime;
    private Date raceStartingTime;

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public Gadget getActiveGadget() {
        return ActiveGadget;
    }

    public void setActiveGadget(Gadget activeGadget) {
        ActiveGadget = activeGadget;
    }

    public void initiatedCountdown(){
        racingState = RacingState.COUNTDOWN_RUNNING;
        countdownStartTime = System.currentTimeMillis();
    }

    public RacingState getRacingState() {
        return racingState;
    }

    public void initiatRaceStart() {
        racingState = RacingState.STARTED;
        raceStartingTime = new Date();
    }
}
