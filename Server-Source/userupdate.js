const mysql = require('mysql');
const connection = require('./dbpool');
const express = require('express');
const router = express.Router();

router.route('/:id').put(function(req,res){
    try{
        console.log('user_update 접근');
        var userinfo = {
            birth: req.body.birth,
            address: req.body.address,
            email: req.body.email
        };
        var query = 'update user set ? where id=?';
        connection.query(query,[userinfo,req.params.id])
            res.status(201).send({
                message : "update ok"
            })
    }
    catch(err){
        console.log('user_update err :',err);
        res.status(500).send({
            message: 'user_update err' + err
        })
    }
})
module.exports = router;