const mysql = require('mysql');
const connection = require('./dbpool');
const register = require('./register');
const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');

router.route('/').post(function(req,res){
    try{
        console.log('login 접근------------------------------');
    
        var Id = req.body.id
        var Password = req.body.password
    
        var query = 'select * from user where id =?';
       
        connection.query(query,Id,function(err,data){
           
            bcrypt.compare(Password,data[0].password).then((values) => {
                if(values){
                    res.status(201).send({
    
                        result: data[0].id,
                        //message : "ok"
                     
                    })
                
                }else{
                    res.status(400).send({
                        message : 'wrong id or password'
                    })
                }
            });
        })
        
        
    }
    catch(err){
        console.log('select err :',err);
        res.status(500).send({
            message: err
        })
    }
});

module.exports = router;