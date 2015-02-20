package de.otto.roboapp.model;

import android.support.annotation.NonNull;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class DataModel {
    String serverIp;
    List<Robo> roboList;
    List<Player> playerList;
    Map<Player, Robo> mappingPlayerToRobo;
    String currenPlayer;


    /* Array zum abspeichern der verfügbaren Roboter */
    public DataModel() {
        roboList = new ArrayList<Robo>();
        playerList = new ArrayList<Player>();
        mappingPlayerToRobo = new HashMap<Player, Robo>();

        addRoboToArray();
   }

    /* Füge verschiedene Roboter dem Array hinzu */
    public void addRoboToArray() {

       roboList.add(new Robo("Supermaschine"));
       roboList.add(new Robo("Hammer"));

    }

    /* Füge Player dem Array hinzu */
    public void addPlayerToArray(String playername) {
        currenPlayer = playername;
        playerList.add(new Player(playername));

    }

    public void assignPlayerToRobo(String roboName){
        mappingPlayerToRobo.put(getPlayerfromString(currenPlayer), getRobofromString(roboName));
        System.out.println("Test" + mappingPlayerToRobo.isEmpty());

    }
    public Robo getRobofromString(String name) {
        for (Robo robo : roboList) {

            if (name.equals(robo.getName())) {

                return robo;
            }
        }
        return null;

    }
    public Player getPlayerfromString(String name) {
        for (Player player : playerList) {
            if (name.equals(player.getName())) {
                return player;
            }
        }
        return null;
    }

    /* Robolist Setter */
    public void setRoboList(ListView selectRoboList) {

    }
    /* RoboList Getter */
    public List<Robo> getRoboList() {

        return roboList;
    }


}
