var WebSocketServer = require('ws').Server
var messageProcessor = require('./messageProcessor.js');
var messageTransmitter = require('./messageTransmitter.js');
var datamodel = require('./datamodel.js');
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

              console.log(datamodel.displayTime() +' received: %s, Message: %s', jsonMessage.eventType, message + "\n");
            } catch (e) {
              console.log("not a valid json message: " + e);
              return;
            }

            var connectedClient = getClientByWsConnection(ws);
            if (jsonMessage.eventType === "connect") {
              messageProcessor.processConnect(connectedClient, jsonMessage);
              messageTransmitter.transmitClients(connectedClients);
            }
            else if (jsonMessage.eventType === "speed") {
              messageProcessor.processSpeed(connectedClient, jsonMessage);
              messageTransmitter.transmitSpeed(connectedClient, datamodel);
            }
            else if (jsonMessage.eventType === "selectRobo") {
              messageProcessor.processRoboSelected(connectedClient, jsonMessage, datamodel);
              messageTransmitter.transmitClients(connectedClients);
            }
            else if (jsonMessage.eventType === "deselectRobo") {
              messageProcessor.processRoboDeselect(connectedClient, datamodel);
              messageTransmitter.transmitClients(connectedClients);
            }
            else if (jsonMessage.eventType === "ready") {
              messageProcessor.processReady(connectedClient, jsonMessage, connectedClients);
              messageTransmitter.transmitClients(connectedClients);
            }
            else if (jsonMessage.eventType === "move") {
              messageTransmitter.transmitMove(connectedClient, jsonMessage, datamodel);
            } else {
              ws.send(JSON.stringify({eventType: "error", data: { message: "unknownEventtype"}}));
            }
        });

        ws.on('close', function(errorcode) {
          var disconnectedClient = getClientByWsConnection(ws);
          if(disconnectedClient.data.type === "robo") {
            console.log("robo losing connection");
            disconnectedClient.data.speed = 0;
            console.log(disconnectedClient.data.speed);
            if (disconnectedClient.data.controlledBy) {
	            var app = datamodel.getClientByName(disconnectedClient.data.controlledBy);
              if(app != null) {
  	            app.data.selectedRobo = null;
  	            app.data.ready = "waiting";
              }
	        }
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
