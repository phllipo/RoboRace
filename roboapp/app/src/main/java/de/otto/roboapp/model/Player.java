package de.otto.roboapp.model;


public class Player implements Comparable<Player>{
    private String name;
    private boolean isReady;

    private int resultTime = 0;

    public int getResultTime() {
        return resultTime;
    }

    public void setResultTime(int resultTime) {
        this.resultTime = resultTime;
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

    @Override
    public int compareTo(Player p) {
        return this.resultTime - p.resultTime;
    }
}
