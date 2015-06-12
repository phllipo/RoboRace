/*
    connectedClients Array, wo alle Clients gespeichert werden wird initiiert
    datamode = {
        connectedClients:
            [{
                webSocketConnection: ws,
                data: {type: "App",
                    name: "test",
                    ready: "true",
                    speed: "100",
                    selectedRobo: "testRobo", <--- hier wird das ganze roboobjekt gespeichert!
                    roboName: "testRobo",
                    startTime: "",
                    endTime:""
                },
                id: connectedClients.length
            }]

    }
*/

var connectedClients = [],
    getConnectedClients = function(){
        return connectedClients;
    },

    playerList = [{name: "hallo", robo: "lightning", time: "15,555 sec"}, {name: "peter", robo: "black", time: "16,435 sec"}],

    getClientByName = function (name) {
        for (i = 0; i <= connectedClients.length; i++) {
            if (connectedClients[i]) {
                if (connectedClients[i].data.name === name) {
                    return connectedClients[i];
                }
            }
        }
        return null;
    },

    displayTime = function() {
        var str = "";

        var currentTime = new Date()
        var hours = currentTime.getHours()
        var minutes = currentTime.getMinutes()
        var seconds = currentTime.getSeconds()

        if (minutes < 10) {
            minutes = "0" + minutes
        }
        if (seconds < 10) {
            seconds = "0" + seconds
        }
        str += hours + ":" + minutes + ":" + seconds + " ";
        if(hours > 11){
            str += "PM"
        } else {
            str += "AM"
        }
        return str;
    }

module.exports = {
    getConnectedClients: getConnectedClients,
    getClientByName: getClientByName,
    displayTime: displayTime,
    playerList: playerList,
};
