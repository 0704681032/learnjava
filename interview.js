
// http://www.cnblogs.com/TomXu/archive/2012/02/10/2342098.html#3187218
// 找出数字数组中最大的元素（使用Match.max函数）
// 转化一个数字数组为function数组（每个function都弹出相应的数字）
// 给object数组进行排序（排序条件是每个元素对象的属性个数）
// 利用JavaScript打印出Fibonacci数（不使用全局变量）
// 实现如下语法的功能：var a = (5).plus(3).minus(6); //2
// 实现如下语法的功能：var a = add(2)(3)(4); //9



function transfer(arr){
	var result = [];
	for (var i = arr.length - 1; i >= 0; i--) {
		result[i] = (function(v){
			return function() {
				console.log(v);
			}
		})(arr[i]);
	};

	return result;
}

var results = transfer([6,78,9,23]);
for (var i = results.length - 1; i >= 0; i--) {
	results[i]();
};


console.log(Math.max.apply(null,[56,7,92,2,34]));//apply求数组的最大值

var objArray = [
	{
		'a':1,
		'b':2
	}, {
		'a':3,
		'b':0,
		'c':-3
	},{
		a:55
	}
];
objArray.sort(function(a,b) {
	var i = Object.keys(a).length;//主要是使用了Object.keys这个新的api
	var j = Object.keys(b).length;
	return i>j?1:(i=j?0:-1);
});
console.log(objArray);

//
var add = function(a) {
	var total = a;
	function addInner(b){
		total+=b;
		//return arguments.callee;
		return addInner;
	}
	// 在sublime下面运行不对 可能是由于sublime基于node.js不允许改写toString 
	//chrome下面运行正常
	addInner.toString = function() {
		return total+"";
	}
	return addInner;
}

console.log(add(2));

console.log(add(2)(3)(4)(6));


Number.prototype.plus=function(a){
	// console.log(this);
	// console.log(typeof this);
	return this+a;
}

Number.prototype.minus=function(a){
	return this-a;
}

var a = (5).plus(3).minus(6);//注意这里的5必须加括号括起来
console.log(a);
