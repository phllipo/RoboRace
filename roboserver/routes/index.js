var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res) {
  res.render('index', { title: "Roborace", connectedClients: req.datamodel.getConnectedClients(), playerList: req.datamodel.playerList});
});

module.exports = router;
