const mysql = require('mysql');
const connection = require('./dbpool');
const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');

router.route('/').post(function(req,res){
    console.log('/register 접근------------------------------');
    
    var Id = req.body.id
    var Password = req.body.password
    var Birth = req.body.birth
    var Address= req.body.address
    var Email = req.body.email
    var Name = req.body.name
    var params = [Id,Password,Birth,Address,Email,Name];
    
    var query = 'insert into user (id,password,birth,address,email,name) values (?,?,?,?,?,?)'
    var check = "select * from user where id=?";
    
    console.log(req.body);
    console.log(req.body.password);
    
    
    connection.query(check,Id,function(err,data){
        if(err){
            console.log("select query error: ", err);
        }
        else if(Password==''){
                res.status(201).send("null");
            }
        else if(data.length==0){
            bcrypt.genSalt(10,function(err,salt){
            bcrypt.hash(Password,salt,function(err,hash){
                 console.log('bcrypt.hash 접근');
                 params[1]=hash;
                 connection.query(query,params,function(err,results){
                    if(err){
                        console.log('db_insert err : ',err);
                     }else{
                         res.status(201).send("OK");  
                     }
               })
            })            
        })
    }
        else{ 
            res.status(201).send("NO")
        }
    })
});      

module.exports = router;