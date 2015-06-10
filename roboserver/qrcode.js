
var os = require('os');
var ifaces = os.networkInterfaces();

var qr = require('qr-image');
var fs = require('fs');

var getLocalIp = function() {
    var ip = "";
    Object.keys(ifaces).forEach(function (ifname) {

      ifaces[ifname].forEach(function (iface) {
       if(ifname === 'wlan0' && iface.family === 'IPv4') {
            ip = iface.address;
            console.log("local ip: " + iface.address);
        }
      });
    });
    return ip
}
// create qr code
var createQr = function() {
    var address = "http://" + getLocalIp() + ":8080/downloads/app-debug.apk";
    var code = qr.image(address, { type: 'png' });
    var output = fs.createWriteStream('public/img/qrcode.png')

    code.pipe(output);
}

module.exports = {
    createQr: createQr,
    getLocalIp: getLocalIp
}