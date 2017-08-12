
const fs = require('fs')
const path = require('path');

function readDir( dir ) {
    //console.log(`dir=>${dir}`);
    fs.readdir( dir ,(err,files) => {
        //console.log(files)
        files.forEach( file => {
            //console.log(`file=>${file}`);
            let file1 = path.join(dir,file);
            fs.stat( file1, (err1,stat) => {
                //console.log(file);
                if ( !stat ) {
                    return 
                }
                if ( stat.isFile() ) {
                    console.log(file)
                } else {
                    readDir( file1 ) 
                }
            })
        })
    });
}

//readDir( "/Users/jyy/Documents/learnnode" )



function readDirPromise( dir ) {
    //console.log(`dir=>${dir}`);
    return new Promise(( resolve,reject) => {
        fs.readdir( dir ,(err,files) => {
            resolve(files)
        });
    })
    
}

function fstatPromise( file ) {
    return new Promise((resolve,reject) => {
        fs.stat( file, (err1,stat) => {
            //console.log(file);
            if ( stat.isFile() ) {
                resolve(true);
            } else {
                resolve(false);
            }
        })
    }) 
}


// let p = readDirPromise("/Users/jyy/Documents/learnnode");

// console.log(p)  

// p.then( files => console.log(files) )

function traverse( dir ) {
    let p = readDirPromise(dir);
    return p.then( files => {
        let promises = files.map( file => {
            let file1 = path.join(dir,file);
            //console.log(`file1->${file1}`)
            return fstatPromise(file1).then( isFile => {
                
                if ( isFile ) {
                    //console.log(`file2->${file1}`)
                    return Promise.resolve(file1)
                }  else {
                    return traverse(file1)
                }
            })
        })

        return Promise.all(promises).then(values => { 
            //console.log(values); // [3, 1337, "foo"] 
            return flatten(values)
        })
    })  
}

function flatten(arr) {
    let result = [];
    arr.forEach(a => {
        if ( Array.isArray(a) ) {
            //console.log('here')
            //console.log(result)
            //console.log(`flatten-.${flatten(a)}`)
            result = result.concat(flatten(a));//这里犯了一个错误
            //console.log(result)
        } else {
            result.push(a); 
        }
    })
    return result
}

let r = traverse("/Users/jyy/Documents/learnnode")
r.then( v => console.log( v ) )
