(function(){

	var ads = [ 1, 2 ,3 ,4 ,5 ,6,7,8,9];//模拟的广告数据 6到18个广告
	//var ads = [ 1, 2 ,3 ,4 ,5 ,6,7,8,9,10,11, 12 ,13 ,14 ,15 ,16,17,18];//6到18个大小
	var len = ads.length;
	var size = 6;//每帧展示6个
	var queue = [];//记录轮播过的广告

	function show() { //切换下一帧
		var currentAds = [];
		for (var i = 0; i < len && currentAds.length < size ; i++) {
			if ( queue.indexOf(ads[i]) === -1 ) {
				currentAds.push(ads[i]);
			} 
		}
		while ( currentAds.length < size && queue.length > 0 ) {
			currentAds.push(queue.shift());
		}
		//TODO 展示广告传递currentAds参数
		console.log(currentAds);
		queue = queue.concat(currentAds);
	}
	show();

	let  n = "rinely" ;
	let  fun  = name => console.log(name +" is clever!");
	fun(n);


})();