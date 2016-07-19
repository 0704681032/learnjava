'use strict';
let n = process.env.N || 50;
let count = 0;

//http://taobaofed.org/blog/2016/07/14/performance-optimization-memoization/

//https://www.nczonline.net/blog/2009/01/27/speed-up-your-javascript-part-3/

function memoizer(fundamental, cache) {
  cache = cache || {};
  let shell = function(arg) {
    if (!cache.hasOwnProperty(arg)) {
      cache[arg] = fundamental(shell, arg);
    }
    return cache[arg];
  };
  return shell;
}

let fibonacci = memoizer(function(recur, n) {
  count++;
  return recur(n - 1) + recur(n - 2);
}, {
  0: 0,
  1: 1
});

let result = fibonacci(n);
console.log('process memory usage', process.memoryUsage());
console.log('count', count);
console.log('result', result);
