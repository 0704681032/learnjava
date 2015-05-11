var fs = require('fs');

var readFile = function (fileName){
  return new Promise(function (resolve, reject){
    fs.readFile(fileName, function(error, data){
      if (error) reject(error);
      //console.log(data);
      resolve(data);

    });
  });
};

var gen = function* (){
  var f1 = yield readFile('/etc/fstab.hd');
  var f2 = yield readFile('/etc/shells');
  //console.log(f1.toString());
  //console.log(f2.toString());
};

spawn(gen);

function spawn(genF) {
  return new Promise(function(resolve, reject) {
    var gen = genF();
    function step(nextF) {
      try {
        var next = nextF();
      } catch(e) {
        return reject(e); 
      }
      if(next.done) {
        return resolve(next.value);
      }
      console.log(next.value);
      Promise.resolve(next.value).then(function(v) {
        step(function() { return gen.next(v); });      
      }, function(e) {
        step(function() { return gen.throw(e); });
      });
    }
    step(function() { return gen.next(undefined); });
  });
}