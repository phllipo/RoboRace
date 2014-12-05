var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res) {
  console.log(req.datamodel.getConnectedClients());
  res.render('index', { connectedClients: req.datamodel.getConnectedClients()});
});

module.exports = router;
