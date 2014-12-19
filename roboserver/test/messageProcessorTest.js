var assert = require('assert'),
    sinon = require('sinon'),
    messageProccesor = require('../messageProcessor.js');

describe('messageProcessor', function(){
    describe('connect', function(){     
        it('should takeover the values into the client connection', function(){
            // given
            var connectedClient = {},
                jsonMessage = {"eventType": "connect", "data": {"clientType": "Test", "name": "TestUnit" }};
            // when
            messageProccesor.processConnect(connectedClient, jsonMessage);
            // then
            assert.equal('TestUnit', connectedClient.name);
            assert.equal('Test', connectedClient.type);
        })
    })
    describe('speed', function(){
        it('should takeover a speedvalue into the connected client', function(){
            // given
            var connectedClient = {},
                jsonMessage = {"eventType": "speed", "data": {"speed": "800"}};
            // when
            messageProccesor.processSpeed(connectedClient, jsonMessage);
            // then
            assert.equal("800", connectedClient.speed);
        })
    })
    describe('selectRobo', function(){
        it('should set selected Robo into the connected client', function(){
            // given
            var connectedClient = {},
                selectedClient = {"clientType": "app", "name": "Sascha", "webSocketConnection": {send: sinon.spy()}},
                jsonMessage = { "eventType": "selectRobo", "data": { "robo": "rob0ne"}},
                datamodel = {
                    getClientByName: function(){
                        return selectedClient;
                    }
                };
            // when
            messageProccesor.processRoboSelected(connectedClient, jsonMessage, datamodel);
            // then
            assert.equal("rob0ne", connectedClient.selectedRobo);
            assert(selectedClient.webSocketConnection.send.called);
        })
    })
})