var WebSocketServer = require('ws').Server
var wss = new WebSocketServer({host: 'localhost', port: 8000});

module.exports = {
  connect: function (datamodel) {
    var connectedClients = datamodel.getConnectedClients();
    console.log(connectedClients);
    wss.on('connection', function(ws) {
        connectedClients.push({
            webSocketConnection: ws,
            id: connectedClients.length
        });
        
        for (i in connectedClients) {
            console.log(connectedClients[i]);
        };
        
        ws.on('message', function incoming(message) {
            var jsonMessage = JSON.parse(message);
            console.log('received: %s', jsonMessage.eventtype);
            var connectedClient = getClientByWsConnection(ws);
            console.log("test:" + connectedClient);
            if (jsonMessage.eventype === "connect") {
                connectedClient.type = jsonMessage.data.clienttype;
            }
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