var processConnect = function(connectedClient, jsonMessage){
        connectedClient.data.name = jsonMessage.data.name;
        connectedClient.data.type = jsonMessage.data.clientType;
        connectedClient.data.ready = jsonMessage.data.ready;
        //console.log(connectedClient.type);
    },
    processSpeed = function(roboclient, jsonMessage){
        roboclient.data.speed = jsonMessage.data.speed;
        //console.log(connectedClient.speed);
    },
    processRoboSelected = function(appclient, jsonMessage, datamodel){
        var controlledRobot = datamodel.getClientByName(jsonMessage.data.robo);
        appclient.data.selectedRobo = controlledRobot.data;
        controlledRobot.data.controlledBy = connectedClient.data.name;
        controlledRobot.webSocketConnection.send(JSON.stringify( {
            eventType: "selectedBy",
            data: {
                playerName: appclient.name 
            }
        }));  
        appclient.webSocketConnection.send(JSON.stringify( {
            eventType: "assignment",
            data: {
               roboName: controlledRobot.name 
           } 
        })); 
    },
    processReady = function(appclient, jsonMessage){
        appclient.data.ready = jsonMessage.data.ready;
    };

module.exports = {
    processConnect: processConnect,
    processSpeed: processSpeed,
    processRoboSelected: processRoboSelected,
    processReady: processReady
};