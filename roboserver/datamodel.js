/*
    connectedClients Array, wo alle Clients gespeichert werden wird initiiert
    datamode = {
        connectedClients: {
            [{
                webSocketConnection: ws,
                data: {type: "App",
                    name: "test",
                    ready: "true",
                    speed: "100",
                    selectedRobo: "testRobo",
                    roboName: "testRobo"
                },
                id: connectedClients.length
            }]
        }
    }
*/

var connectedClients = [],
    getConnectedClients = function(){
        return connectedClients;
    },
    
    getClientByName = function (name) {
        for (i = 0; i <= connectedClients.length; i++) {
            if (connectedClients[i].data.name === name) {
                return connectedClients[i];
            }
        }
        return null;
    }

module.exports = {
    getConnectedClients: getConnectedClients,
    getClientByName: getClientByName
};