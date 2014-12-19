var processConnect = function(connectedClient, jsonMessage){
        connectedClient.name = jsonMessage.data.name;
        connectedClient.type = jsonMessage.data.clienttype;
        console.log(connectedClient.type);
    },
    processSpeed = function(connectedClient, jsonMessage){
        connectedClient.speed = jsonMessage.data.speed;
        console.log(connectedClient.speed);
    },
    processRoboSelected = function(connectedClient, jsonMessage, datamodel){
        connectedClient.selectedRobo = jsonMessage.data.robo;
        var controlledRobot = datamodel.getClientByName(jsonMessage.data.robo);
        controlledRobot.webSocketConnection.send(JSON.stringify( {
            eventtype: "selectedBy",
            data: {
                playerName: "playerName" }
            }));   
    }

module.exports = {
    processConnect: processConnect,
    processSpeed: processSpeed,
    processRoboSelected: processRoboSelected
};