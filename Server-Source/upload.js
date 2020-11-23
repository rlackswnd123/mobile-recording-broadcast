var http = require('http');
var formidable = require('formidable');
var express = require('express');
var router = express.Router();
var fs = require('fs');
const mysql = require('mysql');
const connection = require('./dbpool');
const gm = require('gm');

router.route('/').post(function(req,res){
    console.log('/upload 접속------------------------------');
    
    var query = 'insert into video (video,category,title,image,tag) values (?,?,?,?,?)'
    //const id = req.url;
    //console.log(id);
    
    
    var form = new formidable.IncomingForm();
    //console.dir(req);
    //console.dir("--------------");
    
    form.parse(req, function (err, fields, files) {
        var oldpath = files.filetoupload.path;        
        var oldpath2 = files.imageupload.path;        
        
        console.log(files);
        console.log(fields);
        
        console.log(files.filetoupload.name);
        
        files.filetoupload.name = fields.filename;
        files.imageupload.name = fields.filename;
        
        console.log(files.filetoupload.name);
    
        var newpath = 'C:/red5-server/webapps/oflaDemo/streams/' + files.filetoupload.name + '.mp4';
               
        var newpath2 = 'C:/red5-server/webapps/oflaDemo/streams/' + files.filetoupload.name + '.jpeg';
        
        console.log(newpath);
        console.log(newpath2);
        console.log(fields.tag);
            
        fs.rename(oldpath, newpath, function (err) {
            if (err) throw err;
            //res.write('File Uploaded Success!');
            res.redirect('http://203.237.81.68:3000/upload2.html');
            res.end();
        });
        
        fs.rename(oldpath2, newpath2, function (err) {
            if (err) throw err;
            else gm(newpath2)
            .thumb(100,100,newpath2,function(err){
                if(err) console.log("thumb"+err);
                else console.log('done-thumb');
            })
            //res.write('File Uploaded Success!');
            //res.redirect('http://203.237.81.68:3000/upload2.html');
            //res.end();
        });
        
        connection.query(query,[newpath,fields.category,fields.filename,newpath2,fields.tag],function(err,results){
            if(err){
                console.log('DB_insert err : ',err);
            }
        })
    });
})

module.exports =router;