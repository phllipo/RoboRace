var express = require('express');
var app = express();
var path = require('path');

var routes = require('./routes/index');

var app = express();



app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use('/', routes);

app.listen(3000);


if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});

module.exports = app;

