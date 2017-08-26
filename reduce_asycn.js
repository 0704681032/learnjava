async function reducer(promise, action){
  //console.log(`action ${action}`)
  let res = await promise;
  return action(res);
}

function tick(i){
  console.log(i);
  return new Promise(resolve => setTimeout(()=>resolve(++i), 1000));
}

function continuous(...functors){
  console.log(functors);
  return async function(input){
    return await functors.reduce(reducer, input)
  }
}

function * timing(count = 5){
  for(let i = 0; i < count; i++){
    yield tick;
  }
}

continuous(...timing(10))(0);
