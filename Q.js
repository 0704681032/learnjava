var fs = require('fs'),
    fsPath = require('path'),
    Q = require('q'),
    ffmpeg = require('fluent-ffmpeg');

var walk = function (dir) {
  return Q.ninvoke(fs, 'readdir', dir).then(function (files) {

    return Q.all(files.map(function (file) {

      file = fsPath.join(dir, file);
      return Q.ninvoke(fs, 'lstat', file).then(function (stat) {

        if (stat.isDirectory()) {
          return walk(file);
        } else {
          return [file];
        }
      });
    }));
  }).then(function (files) {
    return files.reduce(function (pre, cur) {
      return pre.concat(cur);
    });
  });
};
//var dir = "F:\\BigData Video\\hadoop7"
//var dir = "F:\\BigData Video\\hadoop7"

var dir = "F:\\尚学堂_肖斌_hadoop视频教程\\";

walk(dir).then(files=>{
  files.filter(f=>{
    return f && ( f.indexOf("mp4")!=-1 || f.indexOf("avi")!=-1 );
  }).forEach(f=>{

      ffmpeg.ffprobe(f, (err, metadata) => {
        console.log(f+"=>"+metadata.format.duration/60);
      });
      
  });
});
