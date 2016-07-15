function add(a,b,c) {
	return a + b + c ;
}

function curry(fn) {
	var len = fn.length;
	//console.log(len);
	return (function fn1(a2){
		//console.log(11);
		return function() {
			var a1 = [].slice.apply(arguments);
			//console.log(a1);
			var a3 = a2.concat(a1);
			//console.log(a3);
			if ( a3.length == len ) {
				return fn.apply(null,a3);
			}
			return fn1(a3);
		}
	})([]);

}

var add1 = curry(add);
console.log(add1(1)(2)(3));

var t1 = add1(2)(3)

var t2 = add1(3)(4)

console.log(t1(4))
console.log(t2(4))


