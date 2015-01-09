var processConnect = function(connectedClient, jsonMessage){
        connectedClient.data.name = jsonMessage.data.name;
        connectedClient.data.type = jsonMessage.data.clientType;
        connectedClient.data.ready = jsonMessage.data.ready;
        //console.log(connectedClient.type);
    },
    processSpeed = function(connectedClient, jsonMessage){
        connectedClient.data.speed = jsonMessage.data.speed;
        //console.log(connectedClient.speed);
    },
    processRoboSelected = function(connectedClient, jsonMessage, datamodel){
        var controlledRobot = datamodel.getClientByName(jsonMessage.data.robo);
        connectedClient.data.selectedRobo = controlledRobot.data;
        controlledRobot.webSocketConnection.send(JSON.stringify( {
            eventType: "selectedBy",
            data: {
                playerName: connectedClient.name 
            }
        }));  
        connectedClient.webSocketConnection.send(JSON.stringify( {
            eventType: "assignment",
            data: {
               roboName: controlledRobot.name 
           } 
        })); 
    },
    processReady = function(connectedClient, jsonMessage){
        connectedClient.data.ready = jsonMessage.data.ready;
    };

module.exports = {
    processConnect: processConnect,
    processSpeed: processSpeed,
    processRoboSelected: processRoboSelected,
    processReady: processReady
};