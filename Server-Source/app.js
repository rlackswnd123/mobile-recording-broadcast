// Express 기본 모듈 불러오기
var express = require('express');
var http = require('http');
var path = require('path');

// Mysql 모듈 사용
var mysql = require('mysql');
var connection= require('./dbpool.js');

// 해시화 모듈 사용
var crypto = require('crypto');
var bcrypt = require('bcrypt');

// Express 미들웨어 불러오기
var bodyParser = require('body-parser');
var static = require('serve-static');

// 오류 핸들러 모듈 사용
var expressErrorHandler =require('express-error-handler');

// 쿠키 모듈 사용
var cookieParser = require('cookie-parser');

// 세션 미들웨어 불러오기
var expressSession = require('express-session');

// 파일 업로드 용 미들웨어
var multer = require('multer');
var fs = require('fs');

// 클라이언트에서 ajax로 요청했을 때 CORS(다중 서버 접속) 지원
var cors = require('cors');

// Express 객체 생성
var app = express();

// Express 기본 설정
app.set('port',process.env.PORT || 3000);


// body-parser를 사용해 application/x-www-form-urlencoded 파싱
app.use(bodyParser.urlencoded({extend : false}));

// body-parser를 사용해 applicaion/json 파싱
app.use(bodyParser.json());

// pubilc 폴더와 uploads 폴더 오픈
app.use('/public',static(path.join(__dirname,'public')));
app.use('/uploads',static(path.join(__dirname,'uploads')));

// cookie-parser 설정
app.use(cookieParser());

// 세션 설정
app.use(expressSession({
    secret: 'my key',
    resave: true,
    saveUninitialized:true
}));

// 클라이언트에서 ajax로 요청했을 때 CORS(다중 서버 접속) 지원
app.use(cors());

// multer 미들웨어 사용: 미들웨어 사용 순서 중요 body-parser -> multer -> router
// 파일 제한 :10개, 1G
var storage = multer.diskStorage({
    destination : function(req,file,callback){
        callback(null,'upload')
    },
    filename : function(req,file,callback){
        callback(null, file.originalname+Date.now())
    }
});

var upload = multer({
    storage: storage,
    limits: {
        files : 10,
        filesize : 1024*1024*1024
    }
});

// 라우터 사용하여 라우팅 함수 등록
// 라우터 객체 참조
var router = express.Router();

// 라우팅 함수 등록 
router.route('/process/login').post(function(req,res){
    console.log('/process/login/:name 처리함.');
    
   // var paramName = req.params.name;
    
    var paramId = req.body.id || req.query.id;
    var paramPassword = req.body.password || req.query.password;
    
    
    if(req.session.user){
        //이미 로그인 된 상태
        console.log('이미 로그인 되어 상품 페이지로 이동합니다.')
        
        res.redirect('/public/product.html');
    }else{
        //세션 저장
        req.session.user={
            id:paramId,
            name:'소녀시대',
            authorized:true
        };
        res.writeHead(200,{'Content-Type':'text/html;charset=utf8'});
        res.write('<h1>로그인 성공.</h1>');
        //res.write('<div><p>Param name : ' + paramName + '</p></div>');
        res.write('<div><p>Param id : ' + paramId + '</p></div>');
        res.write('<div><p>Param password : ' + paramPassword + '</p></div>');
        res.write("<br><br><a href='/process/product'>상품 페이지로 돌아가기</a>");
        
        // 해시를 생성
        /*var hash = crypto.createHash('sha256');
        hash.update(paramId);
        var output1 = hash.digest('hex');
        var password = crypto.createHash('sha256');
        password.update(paramPassword);
        var output2 = password.digest('hex');
        */
    
        var round = 1;
        var query = 'insert into user (id,passwd) values (?,?)'
    
        bcrypt.hash(paramPassword,round,function(err,hash){
        console.log('bcrypt.hash 진입');
        var pw = hash;
        
        connection.query(query,[paramId,pw],function(err,results){
        if(err){
            console.log('db_insert err : ',err);
        }
    });
    });
    
        
        
        
    }
    
});

router.route('/process/logout').get(function(req,res){
    console.log('/process/logout 호출됨.');
    
    if(req.session.user){
        //로그인 된 상태
        console.log('로그아웃합니다.');
        
        req.session.destroy(function(err){
            if(err){throw err;}
            console.log('세션을 삭제하고 로그아웃되었습니다.');
            res.redirect('/public/login.html');
        });
    }else{
        //로그인 안된 상태
        console.log('아직 로그인되어 있지 않습니다.');
        res.redirect('/public/login.html');
    }
});

router.route('/process/showCookie').get(function(req,res){
    console.log('/process/showCookie 호출됨.');
    res.send(req.cookies);
});

router.route('/process/setUserCookie').get(function(req,res){
    console.log('/process/setUserCookie 호출됨.');
    
    // 쿠키 설정
    res.cookie('user',{
        id: 'mike',
        name: '소녀시대',
        authorized : true
    });
    
    // rediect로 응답
    res.redirect('/process/showCookie');
});

router.route('/process/product').get(function(req,res){
    console.log('/process/product 호출됨');
    
    if(req.session.user){
        res.redirect('/public/product.html');
    }else{
        res.redirect('/public/login.html');
    }
});


router.route('/process/photo').post(upload.array('photo',1),function(req,res){
    console.log('/process/photo 호출됨');
    
    try{
        var files =req.files;
        
        console.log('#===== 업로드된 첫번째 파일 정보 =====#')
        console.dir(req.files[0]);
        console.dir('#=====#');
        
        // 현재의 파일 정보를 저장할 변수 선언
        var originalname= '',
            filename='',
            mimetype='',
            size=0;
        
        if(Array.isArray(files)){
            console.log('배열에 들어있는 파일 갯수 : %d',files.length);
            
            for(var index = 0; index<files.length; index++){
                originalname = files[index].originalname;
                filename = files[index].filename;
                mimetype = files[index].mimetype;
                size = files[index].size;
            }
        }else{
            console.log('파일 갯수 : 1');
                originalname = files[index].originalname;
                filename = files[index].name;
                mimetype = files[index].mimetype;
                size = files[index].size;
        }
        console.log('현재 파일 정보 : ' + originalname + ',' + filename + ',' + mimetype + ',' + size);
        
        res.writeHead(200,{'Content-Type':'text/html;charset=utf8'});
        res.write('<h3>파일 업로드 성공</h3>');
        res.write('<hr/>');
        res.write('<p>원본 파일 이름 : ' + originalname + ' -> 저장 파일명' + filename + '</p>');
        res.write('<p>MIME TYPE :' + mimetype + '</p>');
        res.write('<p>파일 크기 : ' + size + '</p>');
        res.end();
    }catch(err){
        console.dir(err.stack);
    }
});


app.use('/',router);

// 모든 router 처리 끝난 후 404 오류 페이지 처리
var errorHandler = expressErrorHandler({
    static : {
        '404': './public/404.html'
    }
});

app.use(expressErrorHandler.httpError(404));
app.use(errorHandler);

http.createServer(app).listen(app.get('port'),function(){
    console.log('express 서버 동작 시작: ' + app.get('port'));
});


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