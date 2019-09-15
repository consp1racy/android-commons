package net.xpece.android.text

import org.junit.After
import org.junit.AssumptionViolatedException
import org.junit.Test
import kotlin.system.measureTimeMillis

class XpTextTest {

    private val text = "A quick brown fox jumped over the lazy dog."

    @After
    fun clearCache() {
        EmphasisCache.clearCache()
    }

    // Took 64 ms with first 4 items.
    // Took 173 ms with all 12 items.
    // This method is 10-40 times faster than the original.
    @Test
    fun benchmarkEmphasize() {
        val time = measureTimeMillis {
            for (i in 0 until 1_000) {
                emphasize(text, "")
                emphasize(text, "j")
                emphasize(text, "ju")
                emphasize(text, "jum")
                emphasize(text, "jump")
                emphasize(text, "jumpe")
                emphasize(text, "jumped")
                emphasize(text, "jump")
                emphasize(text, "jum")
                emphasize(text, "ju")
                emphasize(text, "j")
                emphasize(text, "")
            }
        }
        throw AssumptionViolatedException("Took $time ms.")
    }

//    // Took 2378 ms. Scales linearly.
//    // Would be ~7000 ms with 12 items.
//    @Test
//    fun benchmarkEmphasizeOld() {
//        val time = measureTimeMillis {
//            for (i in 0 until 1_000) {
//                emphasizeOld(text, "jum")
//                emphasizeOld(text, "jump")
//                emphasizeOld(text, "jumpe")
//                emphasizeOld(text, "jump")
//            }
//        }
//        throw AssumptionViolatedException("Took $time ms.")
//    }
}
