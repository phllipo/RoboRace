var transmitClients = function(connectedClients){
    for (i in connectedClients) {
    	if (connectedClients[i].data.type == 'app') {
	    	for (j in connectedClients) {
	    		var clientData = {
	    			eventType: "client",
	    				data: {
	    					clientD: connectedClients[j].data
	    				}
		    	}
		    	console.log(JSON.stringify(clientData));
		    	connectedClients[i].webSocketConnection.send(JSON.stringify(clientData));
	    	}
    	}
	}
};

module.exports = {
    transmitClients: transmitClients
};