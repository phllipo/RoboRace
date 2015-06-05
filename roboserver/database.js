//mongodb connection
var db = require('mongodb').MongoClient;
var collection;

db.connect('mongodb://localhost:27017/roborace', function(err, db) {
    if(!err) {
        console.log('connected to database');
        // create collection if not exists
        db.createCollection('results', function(err, collection) {});
        collection = db.collection('results');
    } else {
        return console.dir(err);
    }
});

var insertResult = function(playername, roboname, time) {
    if(typeof(collection) !== 'undefined') {
        collection.insert({'player': playername, 'robo': roboname, 'time': time}, function(err, records) {
            if(err) {
                console.log("mongodb error: " + records);
            }
        });
    }
}

var getPlayerList = function(res, callback) {
  collection.find().sort({time:1}).toArray(function(err, results) {
    if(err) {
        console.log("ERROR: " + err);
    } else {
        // change millisekonds to time string
        for(r in results) {
            var totalMs = results[r].time;
            var secsWithoutMins = Math.floor(totalMs / 1000);
            var ms = totalMs - (secsWithoutMins * 1000);
            var minutes = Math.floor(secsWithoutMins / 60);
            var seconds = secsWithoutMins - (minutes * 60);

            var result = minutes + " min   " + seconds + " s   " + ms + " ms "

            results[r].time = result
        }
    }
    callback(err, results);
  });
}

var clearCollection = function(callback) {
    collection.remove(function(err, doc) {
        if(err) {
            console.log(err);
        }
        callback(err, doc);
    });
}

module.exports = {
    insertResult: insertResult,
    getPlayerList: getPlayerList,
    clearCollection: clearCollection
}