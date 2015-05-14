function test(str) {
	return eval('('+str+')');
}

var str = "{'a':(function(){alert('aaa');alert('bbb');return 'b';})()}";

console.log(test(str));