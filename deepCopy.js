var deepCopy = function(obj1,obj2) {

	Object.keys(obj2).forEach(function(k,v){
		//console.log(k+":"+v)

		if ( typeof obj2[k]!='object' ) {
			obj1[k] = obj2[k];
		} else { //object
			var o = obj1[k] || {};
			deepCopy(o,obj2[k]);
			obj1[k] = o;
		}

		//console.log(typeof obj2[k]);

	});
}


var obj1 = {
	"a":1,
	"ext": {
		"name1": "jyy",
		 "address": {
		 }
	}
}

var obj2 = {
	"a":2,
	"ext": {
		"name": "zll2",
		 "address": {
		 	't': "tt2",
		 	'y': "yy2"
		 },
		 'j':'j2'
	}
}

deepCopy(obj1,obj2);

console.log(obj1);