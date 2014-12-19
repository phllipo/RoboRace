var processConnect = function(connectedClient, jsonMessage){
        connectedClient.name = jsonMessage.data.name;
        connectedClient.type = jsonMessage.data.clienttype;
        console.log(connectedClient.type);
},
    processSpeed = function(connectedClient, jsonMessage){
        connectedClient.speed = jsonMessage.data.speed;
        console.log(connectedClient.speed);
    };


module.exports = {
    processConnect: processConnect,
    processSpeed: processSpeed
};