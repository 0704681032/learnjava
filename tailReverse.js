function tailReverse(arr) {
	return (function rev(list,ret) {
		return list.length >= 1 ? rev(list.slice(1),list.slice(0,1).concat(ret))  : ret;
	})(arr,arr.slice(0,0));
}

console.log( tailReverse([1,2,3,4]) );
