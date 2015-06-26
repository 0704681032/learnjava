//https://github.com/vuejs/vue-class-component/blob/master/index.js
function clone (val) {
  if (typeof val !== 'object') {
    return val
  } else if (Array.isArray(val)) {
    return val.map(clone)
  } else {
    var res = {}
    Object.keys(val).forEach(function (key) {
      res[key] = clone(val[key])
    })
    return res
  }