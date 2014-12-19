var assert = require('assert');
var messageProccesor = require('../messageProcessor.js');

describe('messageProcessor', function(){
    describe('connect', function(){     
        it('should takeover the values into the client connection', function(){
            // given
            var connectedClient = {},
                jsonMessage = {"eventtype": "connect", "data": {"clienttype": "Test", "name": "TestUnit" }};
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
                jsonMessage = {"eventtype": "speed", "data": {"speed": "800"}};
            // when
            messageProccesor.processSpeed(connectedClient, jsonMessage);
            // then
            assert.equal("800", connectedClient.speed);
        })
    })
})