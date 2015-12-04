var datas = document.querySelectorAll("a.J-media-item.studyvideo");
datas = [].slice.call(datas);
var minute = [];
var second = [];
datas.forEach(function(data) {
	var html = data.innerHTML;
	var times = html.substring( html.indexOf("(")+1, html.indexOf(")"));
	var arr = times.split(":");
    minute.push(arr[0]);
    second.push(arr[1]);
});
var r = ( v1,v2) => { return parseInt(v1,10)+parseInt(v2,10); };
var m = minute.reduce( r );
var s = second.reduce( r );
console.log( m + s/60 );
