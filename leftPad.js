function leftpad(str, len, ch){
  str = '' + str;
  ch = ch || ' ';
  var padlen = len - str.length;
  if(padlen <= 0) return str;
  var padch = padlen & 1 ? ch : '';
   // console.log(padlen);
   //    console.log(ch);
  
  while(padlen >>= 1){
	  ch += ch;
    if(padlen & 1){
      console.log('here');
    	padch += ch;
    }
  }
  return padch + str;
}

console.log(leftpad('abc', 19, 'd'));

//http://code.w3ctech.com/detail/2644 o(logN)版本
