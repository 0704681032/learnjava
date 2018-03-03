package solution
import java.util.*
import kotlin.coroutines.experimental.*

fun plus(o1: Optional<Int>, o2: Optional<Int>): Optional<Int> = `for` {
    val i1: Int = bind(o1)
    val i2: Int = bind(o2)
    yield(i1 + i2)
}

class BindException : Exception()

suspend fun <T> bind(value: Optional<T>): T {
    return suspendCoroutine { cont ->
        if (value.isPresent) {
            cont.resume(value.get())
        } else {
            cont.resumeWithException(BindException())
        }
    }
}

fun <T> yield(value: T) = Optional.of(value)

fun <T> `for`(lambda: suspend () -> Optional<T>): Optional<T> {
    var ret: Optional<T> = Optional.empty()
    val completion: Continuation<Optional<T>> = object : Continuation<Optional<T>> {
        override val context: CoroutineContext = EmptyCoroutineContext
        override fun resume(value: Optional<T>) {
            ret = value
        }
        override fun resumeWithException(exception: Throwable) {
            if(exception !is BindException) throw exception
        }
    }
    lambda.startCoroutine(completion)
    return ret
}