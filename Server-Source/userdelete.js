const mysql = require('mysql');
const connection = require('./dbpool');
const express = require('express');
const router = express.Router();

router.route('/:id').delete(function(req,res){
    
    try{
        console.log('user_delete 접근');
        var Id = req.params.id
        var query = 'delete from user where id =?';
        connection.query(query,Id,function(err,data){
            res.status(201).send({
                message : "delete ok"
            })
        });
    }
    
    catch(err){
        console.log('user_delete err :',err);
        res.status(500).send({
            message: 'user_delete err' + err
        })
    }
})

module.exports = router;