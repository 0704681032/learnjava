//https://github.com/aheckmann/gm

var gm = require('gm');

//var imageMagick = gm.subClass({ imageMagick: true });
gm.prototype.options({
	appPath: '/usr/local/Cellar/graphicsmagick/1.3.20/bin/'
}); //通过查看源代码 知道如何设置执行路径 
    //开始有一个alias gm = git merge 


//var imageMagick = gm.subClass({ imageMagick: true });

gm('/Users/jyy/Desktop/material/n1460845717667.jpg')
	.resize(200, 200, '!')
	.write('/Users/jyy/Desktop/material/hehe.jpg', function(err) {
		//console.log(err);
		if (!err) console.log(' hooray! ');
	});
