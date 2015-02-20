var WebSocketServer = require('ws').Server
var messageProcessor = require('./messageProcessor.js');
var messageTransmitter = require('./messageTransmitter.js');
var port = 8888;
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
        
            try {
              var jsonMessage = JSON.parse(message);
              console.log('received: %s, Message: %s', jsonMessage.eventType, message);
            } catch (e) {
              console.log("not a valid json message: " + e);
              return;
            }

            var connectedClient = getClientByWsConnection(ws);
            if (jsonMessage.eventType === "connect") {
              messageProcessor.processConnect(connectedClients, connectedClient, jsonMessage);
              messageTransmitter.transmitClients(connectedClients);
            }
            else if (jsonMessage.eventType === "speed") {
              messageProcessor.processSpeed(connectedClient, jsonMessage);
              messageTransmitter.transmitSpeed(connectedClient, datamodel);
            }
            else if (jsonMessage.eventType === "selectRobo") {
              messageProcessor.processRoboSelected(connectedClient, jsonMessage, datamodel);
            }
            else if (jsonMessage.eventType === "ready") {
              messageProcessor.processReady(connectedClient, jsonMessage);
            }
            else if (jsonMessage.eventType === "move") {
              messageTransmitter.transmitMove(connectedClient, jsonMessage, datamodel);
            } else {
              ws.send(JSON.stringify({eventType: "error", data: { message: "unknownEventtype"}}));
            }
          for (i in connectedClients) {
              //console.log(connectedClients[i]);
          };
        });

        ws.on('close', function(errorcode) {
          var disconnectedClient = getClientByWsConnection(ws);
          if(disconnectedClient.data.type === "robo") {
            console.log("robo losing connection");
            var App = datamodel.getClientByName(disconnectedClient.data.controlledBy);
            App.data.selectedRobo.name = App.data.selectedRobo.name + "(dc)";
            App.data.selectedRobo.speed = 0;
            App.data.ready = "waiting";
          }
          connectedClients.splice(connectedClients.indexOf(disconnectedClient), 1);
          messageTransmitter.transmitClients(connectedClients);
        });

        function getClientByWsConnection(ws){

            for (i = 0; i <= connectedClients.length - 1; i++) {
                if (connectedClients[i].webSocketConnection === ws) {
                    return connectedClients[i];
                }
            }
            return null;
        }
    });
  }
};
