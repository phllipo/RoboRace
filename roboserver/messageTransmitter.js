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
	transmitMove = function(appclient, jsonMessage, datamodel) {
		if(appclient.data.selectedRobo) {
			var controlledRoboName = appclient.data.selectedRobo.name,
				controlledRobo = datamodel.getClientByName(controlledRoboName)
			controlledRobo.webSocketConnection.send(JSON.stringify(jsonMessage));
		} else {
			console.log("Die sendende App hat zurzeit keinen Robo");
		}
	}

module.exports = {
    transmitClients: transmitClients,
    transmitSpeed: transmitSpeed,
    transmitMove: transmitMove
};