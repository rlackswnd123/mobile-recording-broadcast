const express = require('express');
const router = express.Router();
const mysql = require('mysql');
const connection = require('./dbpool');

router.route('/:name').get(function(req,res){ //카테고리명 입력
    
    var check = "select * from video where category=?";
    
    console.log('/category 접근------------------------------');
    
    //console.log(req.params.name); 
    
    const name = req.params.name 
    
    var result = new Array();
    connection.query(check,name,function(err,data){
        if(err){
            console.log("select query error: ", err);
        }else{
            console.log(data);
            console.log(data.length);
            for(var i=0;i<data.length;i++){
                result[i] = data[i].title
            }
            res.status(201).send(data);
            console.log(result);
        }
    })
})
module.exports = router;