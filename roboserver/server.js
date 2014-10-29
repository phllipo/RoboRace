var connect = require('connect');
var serveStatic = require('serve-static');
connect().use(serveStatic('./public/')).listen(8080);

// Websocket Server

var WebSocketServer = require('ws').Server
var wss = new WebSocketServer({host: 'localhost', port: 8000});

wss.on('connection', function(ws) {
    console.log("Client connected ...");
});

wss.on('close', function(ws) {
    console.log("Client disconnected ...");
});
