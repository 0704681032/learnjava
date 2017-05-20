/**
 *Created by jinyangyang on 20/05/2017 6:06 PM.
 */
fun main(args : Array<String>) {
    println("Hello World")

    var map = hashMapOf<String,String>()
    map.put("j","jyy")
    map.put("z","zll")
    traversrMap(map)

    val oddLength = compose(::isOdd, ::length)
    val strings = listOf("a", "ab", "abc")
    println(strings.filter(oddLength))

}

fun <T> traversrMap(map : Map<T,T>) {
    for ( ( k,v) in map ) {
        println("$k=$v")
    }
}

fun isOdd(x: Int) = x % 2 != 0
fun length(s: String) = s.length

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    //官方的例子
    //return { x -> f(g(x)) }
    //或者
    var f = fun ( x : A ):C {
        return f(g(x))
    }
    return f
}
