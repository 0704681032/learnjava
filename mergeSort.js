var mergeSort = function(a){
	return mergeSortInner(a,0,a.length-1);
}

//TODO 如何在sublime下面debug js 
function mergeSortInner(a,startIndex,endIndex) {
	if ( endIndex-startIndex<=0 ) {
		return ;
	}

	//var middle = (endIndex-startIndex)/2 + startIndex;
	var middle = parseInt((endIndex-startIndex)/2) + startIndex;

console.log("["+startIndex+":"+middle+"]["+(middle+1)+":"+endIndex+"]");

	mergeSortInner(a,startIndex,middle);

	mergeSortInner(a,middle+1,endIndex);



	//merge
	for (var i = middle+1; i <= endIndex; i++) {
		var tmp = a[i];
		var index = i;
		var change = false;
		for(var j=i-1;j>=0;j--){
			if ( a[j] >= tmp ) {
				a[j+1] = a[j];
				change = true;
			} else {
				break;
			}
		}
		if ( change ) {
			a[j+1] = tmp;
		}
	};
	//return a;
}

var data = [6,9,14,4,5,6,19,23,8,10];
console.log(data);
mergeSort(data);
console.log(data);