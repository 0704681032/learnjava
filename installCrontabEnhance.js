(function() {

	//useage: 传入crontab配置文件的绝对地址

	"use strict";
	var readline = require('readline');
	var fs = require('fs');
	//console.log(__dirname);//当前路径
	//console.log(__filename);//当前在执行的js文件路径
	let crontabFile = "crontabTmp";
	//需要读取的crontab文件地址
	let filename = process.argv[2] || '/Users/jyy/Downloads/crontab_sh1.log';

	console.log(filename);

	var existCrontabs = []
    var newCrontabs = [];
	var spawn = require('child_process').spawn,
    ps    = spawn('crontab', ['-l']);//crontab -l 读取已有的crontab
   	
    ps.stdout.on('data', data => {
	    existCrontabs = data.toString().split("\n");
	    installNewCrontab();
	});
	
	ps.stderr.on('data', data =>  console.log('read crontab stderr: ' + data) );

	function installNewCrontab() {

		var rl = readline.createInterface({
		  input: fs.createReadStream(filename),
		  output: null
		});

		rl.on('line', cmd => newCrontabs.push(cmd) ).on('close', ()  => _install() );

		var  _install = function() {
			//检查配置文件是否符合规范
			var project = null;
			var firstLine = newCrontabs[0];
			var endLine = newCrontabs[newCrontabs.length-1];
			var index = firstLine.indexOf(" ");
			project = firstLine.substring(1,index);//substr
			var endLineExpect = "#"+project+" crontab end";
			var firstLineExpect = "#"+project+" crontab begin";
			if ( endLineExpect != endLine || firstLineExpect != firstLine ) {
				console.error("配置文件不符合规范！");
				process.exit(1) ;
			}

			var startIndex = existCrontabs.indexOf(firstLineExpect);
			var endIndex = existCrontabs.indexOf(endLineExpect);
			//console.log(existCrontabs);
			//console.log(startIndex+":"+endIndex);
			if ( startIndex!==-1 && endIndex!==-1 ) {
				existCrontabs.splice(startIndex,endIndex-startIndex+1);
			}
			//console.log(existCrontabs);
			//console.log(newCrontabs);
			//生成新的crontab配置文件
			fs.writeFile(crontabFile, existCrontabs.concat(newCrontabs).join("\n"), 'utf8', err =>  {
				if (err) throw err 
				//写入crontab crontab -e
				var crontab = spawn('crontab', [crontabFile]);
				crontab.stdout.on('data', function (data) {
				  console.log('crontab success: ' + data);
				});
				crontab.stderr.on('data', function (data) {
				  console.log('crontab error: ' + data);
				});
			});
		}


	}
	

})();
