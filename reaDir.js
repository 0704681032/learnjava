var fs = require('fs');
var path = require('path');
var walk = function(dir, done,recursive) {
  var results = [];
  fs.readdir(dir, function(err, list) {
    if (err) return done(err);
    var i = 0;
    (function next() {
      var file = list[i++];
      if (!file)  {
      	results.sort((a,b)=> {
      		console.log(a+":"+a.substring(2));
      		return a.split("/").pop().substring(2).localeCompare(b.split("/").pop().substring(2));
      	});
      	return done(null, results);
      }
      file = path.resolve(dir, file);
      fs.stat(file, function(err, stat) {
        if (stat && stat.isDirectory() && recursive) {
          walk(file, function(err, res) {
            results = results.concat(res);
            next();
          });
        } else {
          if ( file.indexOf("b_") !== -1 || file.indexOf("s_") !== -1 ) {
          	 results.push(file);
          }
          next();
        }
      });
    })();
  });
};


walk("/Users/jyy/Desktop/frontend",function(err,results) {
	console.log(results);
},false);


var walk1 = function(dir, done,recursive) {
  var results = [];
  fs.readdir(dir, function(err, list) {
    if (err) return done(err);
    var i = 0;
    (function next() {
      var file = list[i++];
      if (!file) return done(null, results);
      file = path.resolve(dir, file);
      fs.stat(file, function(err, stat) {
        if (stat && stat.isDirectory() && recursive) {
          walk(file, function(err, res) {
            results = results.concat(res);
            next();
          });
        } else {
          results.push(file);
          next();
        }
      });
    })();
  });
};
