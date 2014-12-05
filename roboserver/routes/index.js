var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res) {
  console.log(req.datamodel.getConnectedClients());
  res.render('index', { title: "Roborace", connectedClients: req.datamodel.getConnectedClients()});
});

module.exports = router;
