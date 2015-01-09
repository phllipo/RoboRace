var WebSocketServer = require('ws').Server
var messageProcessor = require('./messageProcessor.js');
var port = 8887;
var wss = new WebSocketServer({port: port});
console.log("server startet on port " +  port);

module.exports = {
  connect: function (datamodel) {
    
    var connectedClients = datamodel.getConnectedClients();
    wss.on('connection', function(ws) {
      console.log("client connected");
        connectedClients.push({
            webSocketConnection: ws,
            data: {},
            id: connectedClients.length
        });
        
        ws.on('message', function incoming(message) {
            console.log('received: %s', message);
                   
            try {
              var jsonMessage = JSON.parse(message);
              console.log('received: %s', jsonMessage.eventType);
            } catch (e) {
              console.log("not a valid json message");
              return;
            }

            var connectedClient = getClientByWsConnection(ws);
            if (jsonMessage.eventType === "connect") {
              messageProcessor.processConnect(connectedClient, jsonMessage);
            }
            else if (jsonMessage.eventType === "speed") {
              messageProcessor.processSpeed(connectedClient, jsonMessage);
            }
            else if (jsonMessage.eventType === "selectRobo") {
              messageProcessor.processRoboSelected(connectedClient, jsonMessage, datamodel);
            }
            else if (jsonMessage.eventType === "ready") {
              //code  
            } else {
              ws.send(JSON.stringify({eventType: "error", data: { message: "unknownEventtype"}}));
            }
          for (i in connectedClients) {
              //console.log(connectedClients[i]);
          };            
        });
        
        ws.on('close', function(ws) {
            console.log("Client disconnected ...");
        });
        
        function getClientByWsConnection(ws){
            for (i = 0; i <= connectedClients.length; i++) {
                if (connectedClients[i].webSocketConnection === ws) {
                    return connectedClients[i];
                }
            }
            return null;
        }
    });
  }
};