var messageTransmitter = require('./messageTransmitter.js');
var database = require('./database.js');

//database.insertResult("test", "robo", "123");

var processConnect = function(connectedClient, jsonMessage){
//console.log("jsonMessage " + JSON.stringify(jsonMessage));
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
        // console.log("proceeding deselect: " + appclient.data.name);
        if(appclient.data.selectedRobo != null) {
            var roboToDeselect = datamodel.getClientByName(appclient.data.selectedRobo.name);
        }
        delete appclient.data.selectedRobo;
        appclient.data.ready = "false";
        if(roboToDeselect) {
            var speedMessage = {
              eventType: "move",
              data: {
                speed: "0"
              }
            }
            roboToDeselect.data.speed = "0";
            console.log(datamodel.displayTime() + " Send to " + roboToDeselect.data.name + JSON.stringify(speedMessage) + "\n");
            roboToDeselect.webSocketConnection.send(JSON.stringify(speedMessage));
            delete roboToDeselect.data.controlledBy;
        }
    },
    processReady = function(appclient, jsonMessage, connectedClients, datamodel){
        appclient.data.ready = jsonMessage.data.ready;
        var readyClients = 0,
            countRobos = 0,
            assignedClients = [];

        for (i in connectedClients) {
            if (connectedClients[i].data.type == "robo") {
                countRobos+=1;
            } else if (connectedClients[i].data.type == "app" && connectedClients[i].data.selectedRobo && connectedClients[i].data.ready == "true") {
                readyClients+=1;
                assignedClients.push(connectedClients[i]);
                assignedClients.push(datamodel.getClientByName(connectedClients[i].data.selectedRobo.name));
            }
        }
        console.log("Pruefe ob  Robos ready sind..." + countRobos + " - " + readyClients);
        if(countRobos == readyClients) {
            console.log("Alle Robos ready")

            messageTransmitter.transmitCountdownStart(assignedClients);
            setTimeout(function() {
              var now = new Date().getTime();
              console.log("Time: " + now);
              for (i in assignedClients) {
                if(assignedClients[i].data.type == 'app') {
                    assignedClients[i].data.startTime = now;
                }
              }
              messageTransmitter.transmitStart(assignedClients);
            }, 3000);
        }
    },
    processFinish = function(roboClient, connectedClients, jsonMessage, datamodel){
        var roboname = roboClient.data.name,
            finishedClients = [],
            result = [],
            time,
            allFinished = true;

        // get all finished clients
        console.log("roboclient: " + roboname);
        console.log("get all finished clients...");
        for(i in connectedClients) {
            // console.log("get all finished" + connectedClients[i].data.type + " " + typeof connectedClients[i].data.selectedRobo);
            // client must be app and must have a start time
            if(connectedClients[i].data.type == "app" && typeof connectedClients[i].data.startTime != 'undefined') {
                // console.log("Connected client: " + connectedClients[i].data.type);
                // if client has a robo and robo is this robo
                if(typeof connectedClients[i].data.selectedRobo != 'undefined' && connectedClients[i].data.selectedRobo.name == roboname) {
                  console.log("connected client with robo: " + connectedClients[i].data.type)
                  connectedClients[i].data.endTime = new Date().getTime();
                  finishedClients.push(connectedClients[i]);
                  // send data to database
                  database.insertResult(connectedClients[i].data.name, connectedClients[i].data.selectedRobo.name, (connectedClients[i].data.endTime - connectedClients[i].data.startTime));
                } else if (connectedClients[i].data.type == "app" && connectedClients[i].data.endTime) {
                  finishedClients.push(connectedClients[i]);
                }
            }
        }
        // get race-time for every finished client
        for(i in finishedClients) {
            time = finishedClients[i].data.endTime - finishedClients[i].data.startTime;
            finishedClients[i].data.resultTime = time/1000;


            result.push({resultObject: {"name": finishedClients[i].data.name, "time": time}});
        }
        // if all clients finished, free the robos
        for(i in connectedClients) {
            console.log("connected client free robos: " + JSON.stringify(connectedClients[i].data));
            if (connectedClients[i].data.type == "app" && !connectedClients[i].data.endTime) {
                console.log("finished: " + JSON.stringify(connectedClients[i].data));
                allFinished = false;
            }
        }

        // send result msg to finished robo
        messageTransmitter.transmitTimes(finishedClients, result, roboClient);
        if(allFinished) {
            for(i in connectedClients) {
                if(connectedClients[i].data.type == "app") {
                    processRoboDeselect(connectedClients[i], datamodel);
                }
            }
        }
    };

module.exports = {
    processConnect: processConnect,
    processSpeed: processSpeed,
    processRoboSelected: processRoboSelected,
    processRoboDeselect: processRoboDeselect,
    processReady: processReady,
    processFinish: processFinish
};
