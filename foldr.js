const foldr = f => accumulator => ([x, ...xs]) => {
	//console.log(accumulator);
	return x === undefined ?
    accumulator :
    f (x) (foldr(f)(accumulator)(xs))
}
   

const sum = foldr (x => acc => x + acc) (0)
console.log( sum([6,4,8,7,5]) );

const map = f => foldr (x => acc => [f(x),...acc]) ([]);//map函数

const ret = map(x=>2*x)([6,4,7])

console.log("ret:"+ret);
