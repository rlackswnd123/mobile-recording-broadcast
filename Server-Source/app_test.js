// Express 기본 모듈 불러오기
const express = require('express');
const http = require('http');
const path = require('path');
// Express 미들웨어 불러오기
const bodyParser = require('body-parser');
const static = require('serve-static');
// 오류 핸들러 모듈 사용
const expressErrorHandler =require('express-error-handler');

const socketio= require('socket.io');
// cors사용 -클라이언트에서 ajax로 요청하면 CORS 지원
//const cors = require('cors');

const mysql = require('mysql');

const connection= require('./dbpool.js');
const register = require('./register.js');
const login = require('./login');
const user_delete = require('./userdelete');
const user_update = require('./userupdate');
const soketioserver = require('./soketioserver');
const video = require('./video');
const image = require('./image');
const upload = require('./upload');
const category = require('./category');
const tag = require('./tag');
//const fileupload = require('./fileupload');

// Express 객체 생성
const app = express();

// Express 기본 설정
app.set('port',process.env.PORT || 3000);

// body-parser를 사용해 application/x-www-form-urlencoded 파싱
app.use(bodyParser.urlencoded({extend : false}));
// body-parser를 사용해 applicaion/json 파싱
app.use(bodyParser.json());

// app.use(cookieParser());

app.use(static(path.join(__dirname,'public')));


app.use('/register',register);
app.use('/login',login);
app.use('/userdelete',user_delete);
app.use('/userupdate',user_update);
app.use('/soketioserver',soketioserver);
app.use('/video',video);
app.use('/image',image);
app.use('/upload',upload);
app.use('/category',category);
app.use('/tag',tag);
//app.use('/fileupload',fileupload);

// 모든 router 처리 끝난 후 404 오류 페이지 처리
var errorHandler = expressErrorHandler({
    static : {
        '404': './public/404.html'
    }
});

app.use(expressErrorHandler.httpError(404));
app.use(errorHandler);

// cors를 미들웨어로 사용하도록 등록
//app.use(cors());

var server= http.createServer(app);

//var roomCount = new Array();
var io = socketio.listen(server);
//io.set('log level', 2);
io.sockets.on('connection',function(socket){
    console.log('socket connection client');
    socket.on('message',function(data){
        io.sockets.emit('message',data);
        console.log('message connection');
    })
});

server.listen(app.get('port'),function(){
    console.log('Server runnig at : ' + app.get('port'));
});




/*var io = socketio.listen(server);


io.sockets.on('connection', function(socket){    
    socket.on('message', function(data){
        console.log(data);
        if(data != "connect"){
            socket.get('room', function(error, room){
                io.sockets.in(room).emit('message', data);
            });
        } 
    });
    socket.on('join', function(data){
        socket.join(data);
        socket.set('room', data);
        if(typeof roomCount[data] == 'undefined'){
            roomCount[data] = 0;
        }
        roomCount[data]++;
        socket.get('room', function(error, room){
            io.sockets.in(room).emit('roomCount', roomCount[data]);
        });
    });
    socket.on('disconnect', function(){
        socket.get('room', function(error, room){
            if(roomCount[room] <= 0){
                roomCount[room] = 0;
            }
            else{
                roomCount[room]--;
            }
            io.sockets.in(room).emit('roomCount', roomCount[room]);
        });
    });
    process.on('makeRoom', function(){
        io.sockets.emit('makeRoom');
    });
    process.on('changeSWF', function(data){
        io.sockets.emit('changeSWF', data);
    });
    process.on('uploadThumbnail', function(data){
        io.sockets.emit('uploadThumbnail', data);
    });
});

server.listen(app.get('port'), app.get('ip'), function(){
    console.log('Express server listening on port ' + app.get('port'));
});*/
/* 상태코드 별 의미
200 : 요청 전송 성공. (get 성공)
201 : 서버에 데이터 생성 성공. (post 성공)
304 : 리다이렉션 후에 리소스 수정된 거 없음. (오류아님)
400 : request 오류
403 : request 처리 거부
404 : 없는 리소스(url)를 접근할 때 발생 (404 Not Found)
500 : Internal Server Error (서버에서 요청처리 불가)
501 : 서버가 지원하지 않는 요청을 한 경우 (서버에서 API가 구현이 안 된 경우)
503 : 서비스 자체가 불가능한 상태
*/