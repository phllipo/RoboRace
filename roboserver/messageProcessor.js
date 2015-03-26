var messageTransmitter = require('./messageTransmitter.js');

var processConnect = function(connectedClient, jsonMessage){
console.log("jsonMessage " + JSON.stringify(jsonMessage));
        connectedClient.data.name = jsonMessage.data.name;
        connectedClient.data.type = jsonMessage.data.clientType;
        connectedClient.data.ready = jsonMessage.data.ready;
/*     
        Code mit dem reconnectende Robos gefunden werden können
        Damit dieser funktioniert muss der disconnect Code in websocket.js entsprechend angepasst werden
        und connectedClients muss wieder in diese function übergeben werden.

        for(i in connectedClients) {
            var roboDCName = connectedClient.data.name + "(dc)";
            if(connectedClients.length > 1 && connectedClients[i].data.selectedRobo != undefined) {
                if(connectedClients[i].data.selectedRobo.name === roboDCName) {
                    console.log("equal: " + connectedClients[i].data.selectedRobo.name + " / " + roboDCName);
                    var restoreName = connectedClients[i].data.selectedRobo.name;
                    restoreName = restoreName.substring(0, restoreName.length -5);
                    connectedClients[i].data.selectedRobo.name = restoreName;
                    connectedClients[i].data.ready = "true";
                }
            }
        }*/
    },
    processSpeed = function(roboclient, jsonMessage){
        roboclient.data.speed = jsonMessage.data.speed;
    },
    processRoboSelected = function(appclient, jsonMessage, datamodel){
        var controlledRobo = datamodel.getClientByName(jsonMessage.data.robo);
        appclient.data.selectedRobo = controlledRobo.data;
        controlledRobo.data.controlledBy = appclient.data.name;        
    },
    processRoboDeselect = function(appclient, datamodel){
        var roboToDeselect = datamodel.getClientByName(appclient.data.selectedRobo.name);
        appclient.data.selectedRobo = null;
        appclient.data.ready = false;
        roboToDeselect.data.controlledBy = null;
    },
    processReady = function(appclient, jsonMessage, connectedClients){
        appclient.data.ready = jsonMessage.data.ready;
        var readyClients = 0;
        if (connectedClients.length == 4) {
            for (i in connectedClients) {
                if (connectedClients[i].data.ready == "true") {
                    readyClients+=1;
                }
            }
            if (readyClients == 4) {
                messageTransmitter.transmitStart(connectedClients);
            }
        }
    }

module.exports = {
    processConnect: processConnect,
    processSpeed: processSpeed,
    processRoboSelected: processRoboSelected,
    processRoboDeselect: processRoboDeselect,
    processReady: processReady
};