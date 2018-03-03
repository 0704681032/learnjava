package solution

import org.junit.Ignore
import org.junit.Test
import java.util.Optional.empty as none
import java.util.Optional.of as some

class SimpleForComprehensionTest {

    @Test
    fun test1_IntAddition() {
        fun plus(o1: java.util.Optional<Int>,
                 o2: java.util.Optional<Int>): java.util.Optional<Int> =
                `for` {
                    val i: Int = bind(o1)
                    val j: Int = bind(o2)
                    yield(i + j)
                }

        fun testPlus(expected: java.util.Optional<Int>,
                     o1: java.util.Optional<Int>, o2: java.util.Optional<Int>) {
            val actual = plus(o1, o2)
            assert(expected == actual) { "$o1 plus $o2 should be $expected, but you give me a $actual" }
        }

        val nothing = none<Int>()
        testPlus(some(6), some(4), some(2))
        testPlus(nothing, nothing, some(2))
        testPlus(nothing, some(4), nothing)
        testPlus(nothing, nothing, nothing)
    }
}