var transmitClients = function(connectedClients){
	    for (i in connectedClients) {
	    	if (connectedClients[i].data.type == 'app') {
				var clientData = {
		    			eventType: "client",
		    			data: []};
		    	for (j in connectedClients) {
		    		clientData.data.push({clientObject: connectedClients[j].data});
			    }
			    console.log(JSON.stringify(clientData));
			    connectedClients[i].webSocketConnection.send(JSON.stringify(clientData));
	    	}
		}
	},
	transmitSpeed = function(roboclient, datamodel) {
		console.log("Transmit Speed: " + roboclient.data.name);
		var controllingAppName = roboclient.data.controlledBy,
			controllingApp = datamodel.getClientByName(controllingAppName),
			speedData = {
				eventType: "speed",
				data: {
					speed: roboclient.data.speed
				}
			}
		controllingApp.webSocketConnection.send(JSON.stringify(speedData));
	};

module.exports = {
    transmitClients: transmitClients,
    transmitSpeed: transmitSpeed
};