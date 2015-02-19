package de.otto.roboapp.model;

import android.widget.ListView;

import java.util.ArrayList;

public class DataModel {
    String serverIp;
    ArrayList<Robo> roboList;
    Player player;


    /* Array zum abspeichern der verfügbaren Roboter */
    public DataModel() {
        roboList = new ArrayList<Robo>();
        player = new Player();

        addRoboToArray();
    }

    /* Füge verschiedene Roboter dem Array hinzu */
    public void addRoboToArray() {
       roboList.add(new Robo("Supermaschine"));
       roboList.add(new Robo("Hammer"));

    }

    /* Robolist Setter */
    public void setRoboList(ListView selectRoboList) {

    }
    /* RoboList Getter */
    public ArrayList<Robo> getRoboList() {
        return roboList;
    }



}
