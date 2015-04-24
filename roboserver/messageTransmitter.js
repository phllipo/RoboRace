var datamodel = require('./datamodel.js');

var transmitClients = function(connectedClients){
	    for (i in connectedClients) {
	    	if (connectedClients[i].data.type == 'app') {
				var clientData = {
		    			eventType: "client",
		    			data: []};
		    	for (j in connectedClients) {
		    		clientData.data.push({clientObject: connectedClients[j].data});
			    }
			    console.log(datamodel.displayTime() + " Send Client Array: " + JSON.stringify(clientData) + "\n");
			    connectedClients[i].webSocketConnection.send(JSON.stringify(clientData));
	    	}
		}
	},
	transmitSpeed = function(roboclient, datamodel) {
		var controllingAppName, controllingApp;
		console.log("Transmit Speed: " + roboclient.data.name);
		controllingAppName = roboclient.data.controlledBy;
		if(controllingAppName) {
			controllingApp = datamodel.getClientByName(controllingAppName),
			speedData = {
				eventType: "speed",
				data: {
					speed: roboclient.data.speed
				}
			}
			console.log(datamodel.displayTime() + " Send to " + controllingAppName + JSON.stringify(speedData) + "\n");
			controllingApp.webSocketConnection.send(JSON.stringify(speedData));
		}
	},
	transmitMove = function(appclient, jsonMessage, datamodel) {
		if(appclient.data.selectedRobo) {
			var controlledRoboName = appclient.data.selectedRobo.name,
				controlledRobo = datamodel.getClientByName(controlledRoboName)
			console.log(datamodel.displayTime() + "Send to" + controlledRoboName + JSON.stringify(jsonMessage) + "\n");
			controlledRobo.webSocketConnection.send(JSON.stringify(jsonMessage));
		} else {
			console.log("Die sendende App hat zurzeit keinen Robo");
		}
	},
	transmitCountdownStart = function(connectedClients) {
		for (i in connectedClients) {
			var countdownMessage = {
				eventType: "countdownStart"
			};
			console.log(datamodel.displayTime() + " Send to " + connectedClients[i] + JSON.stringify(countdownMessage) + "\n");
			connectedClients[i].webSocketConnection.send(JSON.stringify(countdownMessage));
		}
	},
	transmitStart = function(connectedClients) {
		for (i in connectedClients) {
			var startMessage = {
				eventType: "startRace"
			};
			console.log(datamodel.displayTime() + " Send to " + connectedClients[i] + JSON.stringify(startMessage) + "\n");
			connectedClients[i].webSocketConnection.send(JSON.stringify(startMessage));
		}
	},
	transmitLeftTrack = function(roboclient, jsonMessage, datamodel) {
	    if(roboclient.data.controlledBy) {
		    var controllingAppName = roboclient.data.controlledBy,
		        controllingApp  = datamodel.getClientByName(controllingAppName)
		    console.log(datamodel.displayTime() + " Send to " + controllingApp + JSON.stringify(jsonMessage) + "\n");
		    controllingApp.webSocketConnection.send(JSON.stringify(jsonMessage));
	    }else {
	        console.log("Robo wird von keiner App kontrolliert");
	     }
	},
	transmitTimes = function(finishedClients, result, roboClient) {
			var timeData = {
				eventType: "results",
				data: result
			};
			var moveData = {
				eventType: "move",
				data: [{
					speed: 0
				}]
			};
			for(i in finishedClients) {
				console.log("Time results send: " + JSON.stringify(timeData));
				finishedClients[i].webSocketConnection.send(JSON.stringify(timeData));
			}
			console.log("Set speed to 0 for " + roboClient.data.name + " Message: " + JSON.stringify(moveData));
			roboClient.webSocketConnection.send(JSON.stringify(moveData));
	}


module.exports = {
    transmitClients: transmitClients,
    transmitSpeed: transmitSpeed,
    transmitMove: transmitMove,
    transmitCountdownStart: transmitCountdownStart,
    transmitStart: transmitStart,
    transmitLeftTrack: transmitLeftTrack,
		transmitTimes: transmitTimes
};
