package de.otto.roboapp.model;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataModel {
    String serverIp;
    List<Robo> roboList;
    List<Player> playerList;
    Map<Player, Robo> playerToRoboAssignmentMap;
    String currentPlayerName;

    RacingData racingData;


    /* Array zum abspeichern der verfügbaren Roboter */
    public DataModel() {
        roboList = new ArrayList<Robo>();
        playerList = new ArrayList<Player>();
        playerToRoboAssignmentMap = new HashMap<Player, Robo>();

        createTestData();
   }

    /* Füge verschiedene Roboter dem Array hinzu */
    public void createTestData() {
        Player ari = new Player("Ari");
        playerList.add(ari);
        Player luca = new Player("Luca");
        playerList.add(luca);

        Robo supermaschine = new Robo("Supermaschine");
        roboList.add(supermaschine);
        roboList.add(new Robo("Hammer"));
        playerToRoboAssignmentMap.put(ari, supermaschine);


    }

    public Map<Player, Robo> getPlayerToRoboAssignmentMap() {
        return playerToRoboAssignmentMap;
    }

    public void clearPlayerList () {
        playerList.clear();
    }

    public void clearRoboList() {
        roboList.clear();
    }

    /* Füge Player dem Array hinzu */
    public void addPlayerToArray(String playername) {
        currentPlayerName = playername;
        playerList.add(new Player(playername));

    }

    public void addRoboToArray(String roboname) {
        roboList.add(new Robo(roboname));
    }

    public void assignPlayerToRobo(String roboName){
        playerToRoboAssignmentMap.put(getPlayerfromString(currentPlayerName), getRobofromString(roboName));
        System.out.println("Test" + playerToRoboAssignmentMap.isEmpty());

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


    public RacingData getRacingData() {
        return racingData;
    }
}
