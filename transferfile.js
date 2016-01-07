(function() {
	"use strict"
	var fs = require('fs');
	require('shelljs/global');
	// var destDir = "/Users/jyy/Documents/foxdsp/webadmin/src/main/java/com/foxdsp/model/";
	// //var destDir = "/Users/jyy/Desktop/test";
	// var srcDir = "/Users/jyy/Documents/java8workspace/jFinal2.1/src/com/foxdsp/model/";
	// fs.readdir( destDir, (err, files) => {
	// 	files.forEach( file => {
	// 		console.log(file);
	// 		cp('-Rf', srcDir+file, destDir);
	// 		cp('-Rf', srcDir+'base/Base'+file, destDir+'base/');
	// 	});
	// });
	
	var readDir = "/Users/jyy/Documents/foxdsp/webadmin/src/main/java/com/libfun/common/model/";
	var destDir = "/Users/jyy/Desktop/dest/";
	var srcDir = "/Users/jyy/Documents/java8workspace/jFinal2.1/src/com/foxdsp/model/";
	fs.readdir( readDir, (err, files) => {
		files.forEach( file => {
			console.log(file);
			cp('-Rf', srcDir+file, destDir);
			cp('-Rf', srcDir+'base/Base'+file, destDir+'base/');
			var reg = new RegExp("com.foxdsp.model","gm");
			var replaceMent = 'com.libfun.common.model';
			//TODO 研究/com.foxdsp.model/g 这种方式为什么不可以
			//内部原理 var result = fs.readFileSync(file, 'utf8').replace(regex, replacement);
			//都是调用node.js同步版本的api
			sed('-i', reg,replaceMent, destDir+file);
			sed('-i', reg,replaceMent, destDir+'base/Base'+file);

		});
	});
	
})();
