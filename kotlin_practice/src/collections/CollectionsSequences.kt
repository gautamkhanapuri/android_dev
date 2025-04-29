package collections// the sequences let you avoid building results of intermediate steps, therefore improving the
// performance of the whole collection processing chain.

// However, the lazy nature of sequences adds some overhead which may be significant when processing
// smaller collections or doing simpler computations.

//Iterable completes each collections.step for the whole collection and then proceeds to the next collections.step.

fun mainSequences() {
    val numbersSequence = sequenceOf("four", "three", "two", "one")

    // creating a sequence from an iterable object
    val numbers = listOf("one", "two", "three", "four")
    val numbersSequence1 = numbers.asSequence()
}

fun generateSequenceFromFunction() {
    // seed is the starting point of the sequence.
    val oddNumbers = generateSequence(1) { it + 2 } // `it` is the previous element
    println(oddNumbers.take(5).toList())
//println(oddNumbers.count())     // error: the sequence is infinite

    val oddNumbersLessThan10 = generateSequence(1) { if (it < 8) it + 2 else null}
    println(oddNumbersLessThan10.count())
}

fun generateSequenceFromChunks() {
//    Kotlin internally flattens all values into a single sequence of elements.
    // The output is always just a Sequence of elements, not a “Sequence of List” or “Sequence of Sequence”.
//    yieldAll(listOf(3,5))
//    is similar to
//    for (item in listOf(3,5)) {
//        yield(item)
//    }
    // yield(x) → emit a single value.
    // yieldAll(collection) → emit many values at once (from a list, sequence, iterator, etc.).
    val num = sequence {
        yield(1)
        yieldAll(listOf<Int>(3, 5))
        yieldAll(generateSequence(7) { it + 2  })
    }
    println(num.take(5).toList())
    println(num.takeWhile { it < 25 }.toList())
    // They do not remember that you already consumed 1 and 3 last time.
    //They will re-run the logic fresh every time.
    // A Sequence is not like a live stream that “remembers” where you left off.
    //It’s more like a recipe:
    //	•	Every time you ask for elements (like .take(2)),
    //	•	It starts from scratch, re-runs the logic inside the sequence {} lambda,
    //	•	and emits the elements again from the beginning.
//    •	When you call sequence {} Kotlin compiles it into an anonymous class implementing Sequence<T> and Iterator<T>.
//    •	yield and yieldAll are compiler magic — they turn the block into a state machine.
//    •	Just like how suspend functions work — Kotlin rewrites your code into a continuation under the hood.
}
