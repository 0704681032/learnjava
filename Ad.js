function pdf2cdf(pdf) {
    let cdf = pdf.slice();
     
    for (let i = 1; i < cdf.length - 1; i++)
        cdf[i] += cdf[i - 1];
 
    // Force set last cdf to 1, preventing floating-point summing error in the loop.
    cdf[cdf.length - 1] = 1;
     
    return cdf;
}

function discreteSampling(cdf) {
    let y = Math.random();
    for (let x in cdf)
        if (y < cdf[x])
            return x;
             
    return -1; // should never runs here, assuming last element in cdf is 1
}

//key是各个广告的返回概率,value是具体的广告
var adMap = new Map([["0.12", "A广告"], ["0.4", "B广告"],["0.4", "C广告"],["0.07", "C广告"],["0.01", "D广告"]])


const initAd = dataMap => {
        const pdf = Array.from(dataMap.keys());
        console.log(pdf);
        const cdf = pdf2cdf(pdf);
        console.log(cdf);
        return () => {
            let k = discreteSampling(cdf);
            return adMap.get(k);
        }
}
const getAd = initAd(adMap);
for (let i = 1000; i >= 0; i--) {
    console.log(getAd());
}
