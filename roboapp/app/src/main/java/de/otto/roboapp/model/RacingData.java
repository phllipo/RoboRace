package de.otto.roboapp.model;

import java.util.Date;

public class RacingData {
    private int currentSpeed;
    private Date startTime;
    private Gadget ActiveGadget;

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
}