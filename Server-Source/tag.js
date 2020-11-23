const express = require('express');
const fs = require('fs');
const url = require('url');
const querystring = require('querystring');
const router = express.Router();
const localpath='C:/red5-server/webapps/oflaDemo/streams';
const mysql = require('mysql');
const connection = require('./dbpool');

router.route('/:name').get(function(req,res){ //비디오 제목 입력
    
    var check = "select * from video where title=?";
    
    console.log('/tag 접근------------------------------');
    
    //console.log(req.url);
    //let urlParsed = url.parse(req.url).path;
    //console.log(url.parse(req.url));
    /*fs.readFile(localpath+urlParsed,function(err,data){
        res.writeHead(200,{"Content-Type":"video/mp4"});
        res.write(data);
        res.end();
    })*/
    
    /*console.log(req.params.id);     // id값을 넘겨받아 동영상 재생
    connection.query(check,req.params.id,function(err,data){
        if(err){
            console.log("select query error: ", err);
        }else{
            console.log(data);
            console.log(data[0].video)
            fs.readFile(data[0].video,function(err,data){
            res.writeHead(200,{"Content-Type":"video/mp4"});
            res.write(data);
            res.end();
    
            //res.status(201).send({message: "select ok" });  
            })
        }
    })*/
    
    //console.log(req.params.name); //이미지 제목을 입력받아 동영상 재생
    //console.log(localpath+'/'+req.params.name+'.JPEG');
    //const local = localpath+'/'+req.params.name+'.JPEG' 
    connection.query(check,req.params.name,function(err,data){
        
        if(err){
            console.log("select query error: ", err);
        }else{
            console.log(data);
            console.log(data[0].tag)
            //fs.readFile(data[0].image,function(err,data){
            //res.writeHead(200,{"Content-Type":"image/JPEG"});
            //res.write(data);
            //res.end();
            
            res.status(201).send(data[0].tag);
            console.dir(data);
            //res.status(201).send({message: "select ok" });  
            }
        })
    })
    

module.exports = router;