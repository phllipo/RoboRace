package de.otto.roboapp.model;

import java.util.Date;

public class RacingData {
    final static long COUNTDOWN_TIME = 3000;
    private int currentSpeed;
    private Date startTime;
    private Gadget ActiveGadget;
    private RacingState racingState;
    private long countdownStartTime;

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public long getCountdownRemainingTime(){
        long actualTime = System.currentTimeMillis();
        long passingTime = actualTime - countdownStartTime;

        return COUNTDOWN_TIME - passingTime;
    }

    public RacingState getRacingState() {
        return racingState;
    }

}
