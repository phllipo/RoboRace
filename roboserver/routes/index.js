var express = require('express');
var router = express.Router();
var database = require('../database.js');

/* GET home page. */
router.get('/', function(req, res) {
  database.getPlayerList(res, function(err, results) {
    res.render('index', {
      title: "Roborace",
      connectedClients: req.datamodel.getConnectedClients(),
      playerList: results
    });
  });
  //  res.render('index', { title: "Roborace", connectedClients: req.datamodel.getConnectedClients(), playerList: req.datamodel.playerList, currentTime: req.datamodel.getRacingTime()});

});

/* clear database */
router.get('/cleardb', function(req, res) {
    database.clearCollection(function(err, doc) {
        if(!err) {
            res.redirect('/');
        }
    });
});

module.exports = router;
