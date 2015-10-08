var readline = require('readline'),
    fs = require('fs');

var rl = readline.createInterface({
    input: fs.createReadStream("js全排列.txt"),
    output: process.stdout,
    //output: fs.createWriteStream("js全排列_after.txt"),
    terminal: false
});

var reg = /^\d+\./;

var o = fs.createWriteStream("js全排列_after.txt");
rl.on('line', function(line) {
    //console.log('> ' + line);
    // you won't see the last line here, as 
    // there is no \n any more
    if ( reg.test(line) ) {
    	console.log(line);
    	line = line.substr(line.indexOf(".")+1);
    }
    o.write(line+"\n");
});
