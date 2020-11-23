const mysql = require('mysql');

const connection = mysql.createConnection({
    host : 'localhost',
    user : 'root',
    password: '',
    port : 3306,
    database: 'os'
});

module.exports = connection;
