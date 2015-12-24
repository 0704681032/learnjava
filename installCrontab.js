(function() {

	"use strict";
	var readline = require('readline');
	var fs = require('fs');
	console.log(__dirname);//当前路径
	console.log(__filename);//当前在执行的js文件路径
	let filename = '/Users/jyy/Downloads/crontab_sh.log';//需要读取的crontab文件地址
	let crontabFile = "crontabTmp";


	//读取已有的crontab
	var spawn = require('child_process').spawn,
    ps    = spawn('crontab', ['-l']);
    var existCrontabs = []
    var newCrontabs = [];

    ps.stdout.on('data', data => {
	    existCrontabs = data.toString().split("\n");
	    installNewCrontab();
	});
	
	ps.stderr.on('data', data =>  console.log('read crontab stderr: ' + data) );

	//***********安装新的crontab**************************************************
	function installNewCrontab() {

		var lines = [];
		var rl = readline.createInterface({
		  input: fs.createReadStream(filename),
		  output: null
		});

		rl.on('line', cmd => lines.push(cmd) ).on('close', ()  => processLine() );


		var group = [];
		var  processLine = function() {
			lines.forEach( line => {
				if ( line ) {
					line = line.trim();
					group.push(line);
					if ( line.indexOf("#") !== 0 ) {//不是注释
						addToCrontab(group);
						group = [];
					}
				}
			});
			//console.log(newCrontabs);

			var fileContents = [];

			existCrontabs.forEach( s => fileContents.push(s)  );

			//newCrontabs数组中的每一个元素也是数组
			//数组扁平化
			newCrontabs.forEach( cron => cron.forEach( s => fileContents.push(s) ) );

			//console.log(fileContents);

			//生成新的crontab配置文件
			fs.writeFile(crontabFile, fileContents.join("\n"), 'utf8', err =>  {
				if (err) throw err 

				//写入crontab
				var crontab = spawn('crontab', [crontabFile]);

				crontab.stdout.on('data', function (data) {
				  console.log('crontab success: ' + data);
				});

				crontab.stderr.on('data', function (data) {
				  console.log('crontab error: ' + data);
				});
			});
		}

		var addToCrontab = function(group) {
			//console.log(group);
			var line = group[group.length-1];
			var index = -1 ;
			var grepStr = line ;
			if (  ( index = line.indexOf(">") ) != -1  ) { //重定向的部分不考虑
				grepStr = line.substring(0,index);
			}
			//var grepStr = line.split(" ").slice(0,7).join(" ");
			if ( !contain(grepStr) ) {
				console.log("new:"+group);
				newCrontabs.push(group);//
			}
		}

		var contain = str => {
			var contain = false;
			existCrontabs.forEach( c => {
				if ( c.indexOf(str) == 0 ) {
					contain = true ;
					return false ;
				}
			});
			return contain;
		};

		

	}
	

})();
