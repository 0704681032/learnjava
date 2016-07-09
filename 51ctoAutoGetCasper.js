//var casper = require('casper').create();

var casper = require('casper').create({
    verbose: true,
    logLevel: "debug",
    viewportSize: {
    	width: 1024, height: 1024
    }
});


// casper.start('http://home.51cto.com/index/?reback=http%3A%2F%2Fedu.51cto.com%2F', function() {

// 	this.echo(this.getHTML());

//     this.fill('form#login-form', {
//        'LoginForm[username]': 'jzy19881108',
//        'LoginForm[password]': '0704681032'
//     }, true);



//     this.echo('111');
// });
		

casper.start('http://home.51cto.com/index/?reback=http%3A%2F%2Fedu.51cto.com%2F');

casper.thenEvaluate(function(term) {
	document.querySelector('#loginform-username').setAttribute('value', 'jzy19881108');
    document.querySelector('#loginform-password').setAttribute('value', '0704681032');
    document.querySelector('#login-form').submit();
}, 'CasperJS');


 	// document.querySelector('#username').value = username;
  //   document.querySelector('#password').value = password;
  //   document.querySelector('#submit').click();



casper.then(function() {
    // this.evaluateOrDie(function() {
    //     return /message sent/.test(document.body.innerText);
    // }, 'sending message failed');

    //this.echo('222');

    //this.echo(this.getHTML());

    //require('utils').dump(this.getElementInfo('#BannerBtn'));


    //  this.evaluateOrDie(function() {
    //     // return /message sent/.test(document.body.innerText);
    //     //document.querySelector('#BannerBtn button').click();
    // }, 'sending message failed');

});

casper.then(function() {
    console.log('clicked ok, new location is ' + this.getCurrentUrl());
    //console.log(this.getHTML());

});

casper.waitForSelector('#BannerBtn button', function() {
    //this.captureSelector('twitter.png', 'html');
    //console.log(this.getHTML());
    //this.captureSelector('twitter.png', 'html');
    console.log(document);
    //console.log(document.querySelector('#BannerBtn'));
    //document.querySelector('#BannerBtn button').click();
});


casper.then(function() {
    // Click on 1st result link
    //console.log('44444');
    this.click('#BannerBtn button');
    this.captureSelector('twitter2.png', 'html');

    this.capture('google.png', {
        top: 0,
        left: 0,
        width: 1024,
        height: 1024
    });


    this.captureSelector('twitter.png', '#BannerBtn',{
        format: 'jpg',
        quality: 75
    });

    
});




casper.then(function(term) {
	console.log('33333333');
	console.log(this.getElementInfo('#BannerBtn'));
	require('utils').dump(this.getElementInfo('#BannerBtn'));
}, 'CasperJS');


casper.wait(20000, function() {
    this.echo("I've waited for a second.");
});

//casper.run();

casper.run(function() {
    //this.debugPage();

    // this.wait(5000, function() {
    //     this.echo("I've waited for a second.");
    // });

    this.exit();
});


//this.echo(this.getHTML());

//document.querySelector('#BannerBtn button')


//document.querySelector('#BannerBtn button').click();
