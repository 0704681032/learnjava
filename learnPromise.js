function timeout(time){
	return new Promise(function(resolve,reject) {
		//setTimeout(resolve,time);
		setTimeout(function(){
			resolve('jyy');
		},time);
	});
}


timeout(3000).then(function(data){
	console.log(data);
});


Promise.resolve(timeout(3000)).then(function(data){
	console.log(data);
});