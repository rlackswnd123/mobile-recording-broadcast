var http = require('http');
var fs = require('fs');
var socketio= require('socket.io');
var express = require('express');
var router = express.Router();
var app = express();

router.route('/').get(function(req,res){
    fs.readFile('HTMLPage.html',function(err,data){
    res.writeHead(200,{'Content-Type':'text/html'});
    res.end(data);
    }); 
});

module.exports =router;