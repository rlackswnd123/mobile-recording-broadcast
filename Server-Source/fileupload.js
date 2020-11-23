var http = require('http');
var formidable = require('formidable');
var express = require('express');
var router = express.Router();
var fs = require('fs');

router.route('/').post(function (req, res) {
    
    if (req.url == '/fileupload') {
    var form = new formidable.IncomingForm();
    form.parse(req, function (err, fields, files) {
      var oldpath = files.filetoupload.path;        
        
        console.log(fields);
        
        console.log(files.filetoupload.name);
        
        files.filetoupload.name = fields.filename;
        
        console.log(files.filetoupload.name);
    
        var newpath = 'C:/red5-server/webapps/oflaDemo/streams/' + files.filetoupload.name + '.mp4';
        
        console.log(newpath);
        
        fs.rename(oldpath, newpath, function (err) {
        if (err) throw err;
        res.write('File uploaded and moved!');
        res.end();
      });
 });
}});
    

module.exports =router;