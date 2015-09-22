var cheerio = require('cheerio');
var fs = require('fs');
var http = require('http');
var iconv = require('iconv-lite');
var hostname = "http://v.qq.com";
var imageFolder = "/Users/jyy/Desktop/frontend/";
var options = {
 hostname: hostname,
 port: 80
};

var $;

var getPictureInfo = function(stuNo, callback){
  var html = '';
  var req = http.get("http://v.qq.com", function(res) {
   res.setEncoding('binary');
   res.on('data', function (chunk) {
    html += chunk;
   });
   res.on('end', function(){
    html = iconv.decode(html, 'utf-8');//转换编码
    console.log(html);
    $ = cheerio.load(html); //初始化cheerio实例，用于提取数据
    parseHTML();
   });

  });
  req.end();
};

function parseHTML(){
  $("ul li.list_item").each(function(i, v){
    var images = $(v).find("img");
    if ( images.length == 2) {
        var smallPicUrl = images[0].attribs["lz_src"];
        var picName = smallPicUrl.split("/").pop();
        downloadImage(smallPicUrl,"s_"+picName);//小图
        downloadImage(images[1].attribs["data-src"],"b_"+picName);//大图
    }
  });
}

function downloadImage(path, filename){

  if (typeof path !== "string") {
    return false;
  }

  //若图像已经存在，则返回文件名
  if (fs.existsSync(imageFolder + filename)) {
    //console.log('image for ' + filename + ' existed');
    return filename;
  }


  var req = http.get(path, function(res){

    //初始化数据！！！
    var binImage = '';

    res.setEncoding('binary');
    res.on('data', function(chunk){
      binImage += chunk;
    });

    res.on('end', function(){

      if (!binImage) {
        console.log('image data is null');
        return null;
      }

      fs.writeFile(imageFolder + filename, binImage, 'binary', function(err){
        if (err) {
          console.log('image writing error:' + err.message);
          return null;
        }
        else{
          console.log('image ' + filename + ' saved');
          return filename;
        }
      });
    });

    res.on('error', function(e){
      console.log('image downloading response error:' + e.message);
      return null;
    });
  });

  req.end();

}


getPictureInfo();

