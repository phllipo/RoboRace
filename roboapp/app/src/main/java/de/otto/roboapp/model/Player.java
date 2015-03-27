package de.otto.roboapp.model;


public class Player {
   private String name;
   private boolean isReady;

    public Player () {

  }

  public Player(String name, boolean isReady) {
      this.name = name;
      this.isReady = isReady;

  }

    public String getName() {
        return name;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        this.isReady = ready;
    }
}
