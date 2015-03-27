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

    public String currentPlayerName;

    RacingData racingData = new RacingData();

    /* Array zum abspeichern der verfügbaren Roboter */
    public DataModel() {
        roboList = new ArrayList<Robo>();
        playerList = new ArrayList<Player>();
        playerToRoboAssignmentMap = new HashMap<Player, Robo>();

        //createTestData();
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
    public void assignPlayerToRobo(String playerName, String roboName){
        getPlayerByName(playerName).setAssigned(true);
        getRoboByName(roboName).setAssigned(true);
        playerToRoboAssignmentMap.put(getPlayerByName(playerName), getRoboByName(roboName));
        System.out.println("Test" + playerToRoboAssignmentMap.isEmpty());
    }

    public void deselectPlayerFromRobo(String playerName, String roboName){
        getPlayerByName(playerName).setAssigned(false);
        getRoboByName(roboName).setAssigned(false);
        playerToRoboAssignmentMap.remove(this);
        System.out.println("Test" + playerToRoboAssignmentMap.isEmpty());
    }

    public Robo getRoboByName(String name) {
        for (Robo robo : roboList) {
            if (name.equals(robo.getName())) {
                return robo;
            }
        }
        return null;

    }
    public Player getPlayerByName(String name) {
        for (Player player : playerList) {
            if (name.equals(player.getName())) {
                return player;
            }
        }
        return null;
    }
    public List<Player> getUnassignedPlayer() {
        ArrayList<Player> unassignedPlayerList = new ArrayList<>();
            for (Player player : playerList) {
                 if(!playerToRoboAssignmentMap.containsKey(player)) {
                  unassignedPlayerList.add(player);
            }
        }
        return unassignedPlayerList;
    }

    public List<Player> getAssignedPlayer() {
        ArrayList<Player> assignedPlayerList = new ArrayList<>();
        for (Player player : playerList) {
            if(playerToRoboAssignmentMap.containsKey(player)) {
                assignedPlayerList.add(player);
            }
        }
        return assignedPlayerList;
    }

    public List<Robo> getAssignedRobo() {
        ArrayList<Robo> assignedRoboList = new ArrayList<>();
        for (Robo robo : roboList) {
            if(playerToRoboAssignmentMap.containsValue(robo)) {
                assignedRoboList.add(robo);
            }
        }
        return assignedRoboList;
    }

    /* Robolist Setter */
    public void setRoboList(ListView selectRoboList) {

    }

    /* RoboList Getter */
    public List<Robo> getRoboList() {
        return roboList;
    }
    public  List<Player> getPlayerList() {
        return  playerList;
    }

    public RacingData getRacingData() {
        return racingData;
    }


    public String getCurrentPlayerName() {
        return currentPlayerName;
    }
}
