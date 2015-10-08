(function(){
	//一个人上楼梯,每次可走一步或者两步,走n步共有多少种解法
	var  arr = [];
	var resultArr = [];//存储所有解法
	var targetCount = 6;//走到6楼
	var getSum = function (a) { //求数组的和
		return a.reduce( (a,b)=>{return a+b;},0 );
	}
	function cal() {
		var sum = getSum(arr);
		if ( sum > targetCount ) {
			return ;
		}
		if ( sum == targetCount ) { //找到一种解法
			var solve = arr.concat();
			//console.log(solve);//打印出解法
			resultArr.push(solve);
			return ;
		}
		arr.push(1);
		cal();
		arr.pop(1);
		arr.push(2);
		cal();
		arr.pop(2);
	}
	cal();
	console.log(resultArr);
}());
