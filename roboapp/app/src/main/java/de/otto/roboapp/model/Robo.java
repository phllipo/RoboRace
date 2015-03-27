package de.otto.roboapp.model;


public class Robo {

   private String name;
   private boolean assigned;

    public Robo(String name) {
        this.name = name;
        assigned = false;

    }

    public String getName() {
        return name;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
}
