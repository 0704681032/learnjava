var readline = require('readline'),
    fs = require('fs'),
    f = '/Users/jyy/Desktop/audienx09',
    readStream = fs.createReadStream(f),
    rl = readline.createInterface(readStream,null,null,false),
    result = {},
    strArray,i,str1,str2;

rl.on('line', function(line) {

    if ( line.indexOf('Found') != -1 ) {
      i = line.indexOf('Found')+"Found".length;
      str1 = line.substring(0,i+1);
      str2 = line.substring(i+1).trim();
    }  else {
        strArray = line.split(/\s+/);
        str1 = strArray[0];
        str2 = strArray[1];
    }

    //console.log(str1+"=>"+str2);

    result[str1] = ( result[str1] || 0) + parseInt(str2);

    //

    //console.log(result);
   //console.log(line);
}).on('close', function() {
  console.log(f);
  //for ( var k in result ) {
    //console.log(k+"=>"+result[k]);
  //}
  console.log(result);
  //process.exit(0);
});

