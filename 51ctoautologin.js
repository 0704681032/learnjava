var   request = require('request');
var j = request.jar();
var request = request.defaults({jar: true});
var Q = require("q");
//var FileCookieStore = require('tough-cookie-filestore');
// NOTE - currently the 'cookies.json' file must already exist!
//var j = request.jar(new FileCookieStore('cookies.json'));
//request = request.defaults({ jar : j });
var rand = Math.floor(Math.random()*100000000).toString();
 
var data = {
      url:'http://home.51cto.com/index.php?s=/Index/doLogin', 
      form: {
        'email': 'xxxxx',
        'passwd': 'yyyyyyyy'
      }
      
};
//jar: j

var options = {
  url: 'http://edu.51cto.com/index/task',
  headers: {
    'User-Agent': 'request'
  }
};
//  jar: j


function sign() {
  // console.log("444:"+t1);
  //var cookie = request.cookie(t1);
  //   var url = options.url;
  //   j.setCookie(cookie, options.url);
    request.get(options, function(err1,res1,body1) {
        // console.log(err.code === 'ETIMEDOUT');
        // // Set to `true` if the timeout was a connection timeout, `false` or
        // // `undefined` otherwise.
        // console.log(err.connect === true);
        console.log("O(∩_∩)O~:"+body1);
        process.exit(0);
    });
}

////cookie_string = j.getCookieString(data.url); // "key1=value1; key2=value2; ..."
  //cookies = j.getCookies(data.url);
  //console.log(cookie_string+"=>"+cookies);

     //console.log( typeof httpResponse.headers['set-cookie'] ) ;
     //t1 = httpResponse.headers['set-cookie'].toString();
     //console.log(t1);
     //console.log(JSON.stringify(t1));

//学习q,练习:51cto自动登录领取积分脚本
request.post(data, (err,httpResponse,body) => {
     let req = u => {
        var deferred = Q.defer();
        request.get(u, function(err1,res1,body1) {
            console.log("完成请求 "+u+"=>"+body1)
            deferred.resolve(body1);
        });
        return deferred.promise;
     }
     var scripts = body.split("</script>");
     var promises = [];//所有的异步请求
     //登陆成功后，会经过一个过渡页面，这个页面或想其他的子站点发请求来告知是否通过登陆验证
     scripts.filter(script => {
        return script.indexOf('<script type="text/javascript" ')==0;
     }).forEach(script => {
        script = script.replace('<script type="text/javascript"  src="',"").replace('">', '');
        promises.push(req(script));
     });
     Q.all(promises).then(() => {//所有异步操作完成后,开始自动签到
        console.log("开始签到领取积分...");
        sign();//登录
     });


     
   
  }
);

