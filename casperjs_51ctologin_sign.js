/*eslint strict:0*/
/*global CasperError, console, phantom, require*/

var links = [];
var results = [];
var results1 = [];

var casper = require("casper").create();

var fs = require('fs');

var url = 'http://home.51cto.com/index.php?s=/Index/index/reback/http%253A%252F%252Fedu.51cto.com%252F';
var data = {
    url: url,
    u: "xxxx",
    p:"yyyy"
}

function getLinks() {
    // //this.echo(document.body);
    // var links = document.querySelectorAll("div.c-gap-top a");
    // //var links = document.querySelectorAll("h3.r a");
    // return Array.prototype.map.call(links, function(e) {
    //     try {
    //         // google handles redirects hrefs to some script of theirs
    //         //return (/url\?q=(.*)&sa=U/).exec(e.getAttribute("href"))[1];
    //         return e.getAttribute("href");
    //     } catch (err) {
    //         return e.getAttribute("href");
    //     }
    // });
    //casper.echo('get links');

    var filter, map;
    filter = Array.prototype.filter;
    map = Array.prototype.map;
    return map.call(filter.call(document.querySelectorAll("a"), function(a) {
        return (/^http:\/\/.*/i).test(a.getAttribute("href"));
    }), function(a) {
        return a.getAttribute("href");
    });
}

//<a href="http://edu.51cto.com/user/user_id-1964396.html" 
//class="white" target="_blank" id="userName">jzy19881108</a>

//http://edu.51cto.com/user/login.html

function getUserInfo() {
        //this.echo('here12');
    casper.echo('here12');
    results.push(document.querySelectorAll("*"));
    results1.push(document.querySelector('a[href="http://edu.51cto.com/user/login.html"]'));
}

casper.start(data.url, function() {
    // search for 'casperjs' from google form
    // try {
    //            this.echo(document.body.innerHTML);

    //     } catch (err) {
    //         this.echo(err);
    //     }
    // this.evaluate(function() {
    //     this.echo(document.body.innerHTML);
    // });

    //this.fill('form[action="/s"]', { wd: "java" }, true);

    links = this.evaluate(getLinks);
    //http%3A%2F%2Fedu.51cto.com%2F rebback
    //        'input[name="rebback"]':  'http%3A%2F%2Fedu.51cto.com%2F'

    this.fillSelectors('form#login-user-form', {
        'input[name="email"]':   data.u ,
        'input[name="passwd"]':    data.p,
        'input[name="autologin"]':         true
    }, true);
});

casper.then(function() {
    // aggregate results for the 'phantomjs' search
    this.echo('here1');
    this.echo(this.getCurrentUrl());
    //this.echo(this.getHTML());

    //this.evaluate(getUserInfo);
    //links = this.evaluate(getLinks);
    //<a href="http://edu.51cto.com/user/login.html" 
    //id="signIn" target="_blank" class="btn fl">签到</a>
    var html = this.getHTML();
    //this.echo(html.indexOf("已签到"))
    //fs.write("result.html", html , 'w');
    this.waitUntilVisible("#signIn", function() {

        this.echo("登录成功");
        require('utils').dump(this.getElementInfo('#signIn'));
        //this.echo(this.getElementInfo('#signIn'));
        // hide play button
        var t = this.getHTML('#signIn');
        this.echo(t);
        this.echo(t === '已签到');
        if ( t !== '已签到' ) {
            this.click("#signIn");
        } 
        //fs.write("result.html",html, 'w');

    });

});

casper.then(function() {
    this.captureSelector('weather'+(+new Date())+'.png', '#LoginUserInfo');
    fs.write("result.html",this.getHTML(), 'w');
    console.log('clicked ok, new location is ' + this.getCurrentUrl());
});

// casper.then(function() {
//     // aggregate results for the 'casperjs' search
//     links = this.evaluate(getLinks);
//     // now search for 'phantomjs' by fillin the form again
//     //this.fill('form[action="/search"]', { q: "phantomjs" }, true);
//     this.fill('form[action="/s"]', { wd: "js" }, true);
// });

// casper.then(function() {
//     // aggregate results for the 'phantomjs' search
//     links = links.concat(this.evaluate(getLinks));
// });

casper.run(function() {
    // echo results in some pretty fashion
    //this.echo(links.length + " links found:");
    //this.echo(" - " + links.join("\n - "));
    //this.echo(results);
    //this.echo(results1);
    //this.echo("====");
    this.exit();
});
