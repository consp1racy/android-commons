package net.xpece.android.text

import org.junit.AssumptionViolatedException
import org.junit.Test
import java.util.*
import kotlin.system.measureTimeMillis

class EmphasisTest {

    private val text = "A quick brown fox jumped over the lazy dog."

    // Took 64 ms with first 4 items.
    // Took 173 ms with all 12 items.
    // This method is 10-40 times faster than the original.
    @Test
    fun benchmarkEmphasize() {
        val emphasis = Emphasis()
        val time = measureTimeMillis {
            for (i in 0 until 1_000) {
                emphasis.emphasize(text, "")
                emphasis.emphasize(text, "j")
                emphasis.emphasize(text, "ju")
                emphasis.emphasize(text, "jum")
                emphasis.emphasize(text, "jump")
                emphasis.emphasize(text, "jumpe")
                emphasis.emphasize(text, "jumped")
                emphasis.emphasize(text, "jump")
                emphasis.emphasize(text, "jum")
                emphasis.emphasize(text, "ju")
                emphasis.emphasize(text, "j")
                emphasis.emphasize(text, "")
            }
        }
        throw AssumptionViolatedException("Took $time ms.")
    }

    @Test
    fun benchmarkSingleSubjectWithSingleEmphasis() {
        val emphasis = Emphasis()
        val time = measureTimeMillis {
            emphasis.emphasize(text, "w")
        }
        throw AssumptionViolatedException("Took $time ms.")
    }

    @Test
    fun benchmarkMultipleSubjectsWithSingleEmphasis() {
        val texts = listOf(
                "Who let the dogs out",
                "Norwegian wood",
                "I'm Darth Vader from planet Vulkan",
                text,
                "How much wood would a woodchuck chuck if woodchuck could chuck wood?",
                "Expecto patronum!",
                "Nun liebe Kinder, gib fein Acht!",
                "Lorem ipsum dolor sit amet",
                "Yo mamma is so fat...",
                "Harry Potter and the Prisoner of Azkaban",
                "EA SPORTS"
        ).map { it.toLowerCase(Locale.US) }
        val emps = listOf(
                "w"//, "wo", "woo", "wood", "woo", "wo", "w"
        )
        val emphasis = Emphasis()
        val time = measureTimeMillis {
            texts.forEach { text ->
                emps.forEach { emp ->
                    emphasis.emphasize(text, emp)
                }
            }
        }
        throw AssumptionViolatedException("Took $time ms.")
    }

//    // Took 2378 ms. Scales linearly.
//    // Would be ~7000 ms with 12 items.
//    @Test
//    fun benchmarkEmphasizeOld() {
//    }
}
