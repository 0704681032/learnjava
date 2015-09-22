var fs = require('fs')
  , gm = require('gm').subClass({imageMagick: true});

// resize and remove EXIF profile data
gm('1_big.jpeg')
.resize(700, 120, '!')
.noProfile()
.write('1_big_resize.jpeg', function (err) {
  err && console.log(err);
  if (!err) console.log('done');
});
