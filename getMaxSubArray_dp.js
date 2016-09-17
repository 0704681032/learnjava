function getMaxSubArr( arr ) {
	let max = arr[0];
	let dp = [];
	dp[0] = max ;
	let result = [];
	result[0] = max;

	let len = arr.length;
	for( let i = 1 ; i < len ; i++ ){
		let tmp = dp[i-1] + arr[i];
		if ( tmp >= arr[i] ) { 
			dp[i] = tmp;
		} else {
			result = [];//reset
			dp[i] = arr[i];
		}
		result.push(i);
		max = Math.max.call(null,max,dp[i]);

	}
	console.log(dp);
	console.log(result);
	return max;
}

console.log(getMaxSubArr([2,-3,4,5,-4,-6,7,-8]));
