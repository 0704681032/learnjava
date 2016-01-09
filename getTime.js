var ffmpeg = require('fluent-ffmpeg');
var path = require('path');
const fs = require('fs');
//console.log(ffmpeg);
//console.log(__dirname);
// var file = path.join(__dirname,'‪07.avi');
// console.log(file);
// ffmpeg.ffprobe("‪01.avi", function(err, metadata) {
//     console.dir(metadata);
// });

// var dir = "F:\\BigData Video\\hadoop7\\day07"
// fs.readdir(dir, (err, files) => {
// 	files.forEach( file => {
// 		let f = path.join(dir,file);
// 		ffmpeg.ffprobe(f, (err, metadata) => {
//     		//console.log(f+"=>"+metadata.format.duration/60);
// 		});

// 	})
// })

var dir = "F:\\BigData Video\\hadoop7"

traverse(dir);

function traverse(dir,cb) {//异步遍历文件
	let ret = [];
	_traverse(dir,{});
	function check(o) {
		o.waitcount--;
		if ( o.waitcount === 0 ) {
			if ( !o.parent ) { //已经到达顶级目录,异步操作完成
				console.log(ret);
				cb && cb(ret);
				return ret ;
			} else {
				check(o.parent);
			}
		}
	}
	function _traverse(dir,parent) {
		fs.readdir(dir, (err, files) => {
			parent.waitcount = files.length;
			files.forEach( file => {
				let f = path.join(dir,file);
				let stat = fs.statSync(f);
				if ( stat.isFile() ) {
					ret.push(f);
					check(parent);
				} else if ( stat.isDirectory() ) {
					let o = {
						parent: parent
					};
					_traverse(f,o);
				}
			});
		});
	}
}
