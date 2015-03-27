package de.otto.roboapp.model;


public class Player {
   private String name;
   private boolean assigned;
   private boolean status;

    public Player () {
      assigned = false;

  }

  public Player(String name) {
      this.name = name;

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
