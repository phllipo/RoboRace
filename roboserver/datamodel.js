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