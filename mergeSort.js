'use strict';

let n = process.env.N || 100;
let isMemoized = process.env.M;
let test = [];

function merge(left, right) {
  let result = [];

  while (left.length > 0 && right.length > 0) {
    if (left[0] < right[0]) {
      result.push(left.shift());
    } else {
      result.push(right.shift());
    }
  }

  return result.concat(left).concat(right);
}

function mergeSort(items) {
  if (items.length == 1) {
    return items;
  } else {
    let middle = Math.floor(items.length / 2);
    let left = items.slice(0, middle);
    let right = items.slice(middle);
    return merge(mergeSort(left), mergeSort(right));
  }
}

for (let i = 0; i < n; i++) {
  test.push(Math.random() * 10);
}

let result;
if (isMemoized) {
  let memoize = require('./zakas-memo.js');
  mergeSort = memoize(mergeSort);
  result = mergeSort(test);
} else {
  result = mergeSort(test);
}
console.log('process memory usage', process.memoryUsage());
