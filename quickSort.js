var quickSort = function(arr){
	if ( arr.length <=1 ) {
		return arr;
	}
	var left = [];
	var right = [];
	var pivot = arr[0];
	for (var i = arr.length - 1; i >= 1; i--) {
		if ( arr[i] <= pivot ) {
			left.push(arr[i]);
		} else {
			right.push(arr[i]);
		}
	}
	return quickSort(left).concat(pivot).concat(quickSort(right));
}

var data = [6,9,14,4,5,6,19,23,8,10];
console.log(quickSort(data));
