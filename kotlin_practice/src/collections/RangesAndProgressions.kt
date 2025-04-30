package collections

fun mainRangesAndProgressions() {
    basicRangeExamples()
    rangeToExamples()
    rangeUntilExamples()
    rangeWithStepsExamples()
}

// ClosedFloatingPointRange is iterable by default. Because between any 2 floating point numbers, there
// are infinite number of floating point numbers between them, so the compiler cannot iterate over it
// so I am defining an extension function that will define the collections.step while iterating over such a range.
fun ClosedFloatingPointRange<Double>.step(stepSize: Double): Sequence<Double> {
    require(stepSize > 0) { "Step size must be positive." }
    return generateSequence(start) {
        val next = it + stepSize
        if (next <= endInclusive) next else null
    }
}

// Demonstrates basic range creation using '..' operator
// Format: start..end (inclusive)
fun basicRangeExamples() {
    // Integer ranges
    val numberRange = 1..10  // Creates range from 1 to 10 (inclusive)
    println("Numbers in range: ${numberRange.toList()}")

    // Character ranges
    val letterRange = 'A'..'F'  // Creates range from A to F (inclusive)
    println("Letters in range: ${letterRange.toList()}")

    // Check if value is in range
    println("Is 5 in range? ${5 in numberRange}")  // true
    println("Is 11 in range? ${11 in numberRange}") // false
}

// Demonstrates rangeTo() function which is equivalent to '..' operator
// Format: start.rangeTo(end) - inclusive
fun rangeToExamples() {
    // Integer rangeTo
    val numbers = 1.rangeTo(5)  // Same as 1..5
    println("Numbers using rangeTo: ${numbers.toList()}")

    // Double rangeTo
    val doubles = 1.0.rangeTo(5.0)
    println("Doubles using rangeTo: ${doubles.step(1.0).toList()}")
}

// Demonstrates rangeUntil() function which creates exclusive upper bound range
// Format: start.rangeUntil(end) - exclusive of end value
fun rangeUntilExamples() {
    // Creates range from 1 to 4 (5 is excluded)
    val untilRange = 1.rangeUntil(5)  // Same as 1..<5
    println("Range until 5: ${untilRange.toList()}")

    // Using with characters
    val charUntilRange = 'A'.rangeUntil('E')
    println("Chars until E: ${charUntilRange.toList()}")
}

// Demonstrates ranges with custom steps
fun rangeWithStepsExamples() {
    // Step with '..' operator
    val steppedRange = (1..10 step 2)  // Steps of 2: 1, 3, 5, 7, 9
    println("Stepped range: ${steppedRange.toList()}")

    // Step with rangeTo
    val steppedRangeTo = 1.rangeTo(10).step(3)  // Steps of 3: 1, 4, 7, 10
    println("Stepped rangeTo: ${steppedRangeTo.toList()}")

    // Reverse range
    val reverseRange = 10 downTo 1  // Creates descending range
    println("Reverse range: ${reverseRange.toList()}")
}

// AI generated up till this.

fun basicRangeExamples1() {
    println(4 in 1..4) // rangeTo function. Includes the both start and end values.
    println(4 in 1..< 4) // rangeUntil function. Excludes the end value.

    // Use in for loop to iterate over range.
    for (i in 1..10) {
        println(i)
    }

    // using the until function. ..< is similar to until except that ..< is an operator, whereas until is a function.
    for (i in 1 until 10) {
        println(i)
    }
}

fun reverseRange() {
    // both ends are included.
    for (i in 10 downTo 1) {
        println(i)
    }

    // In all above examples, collections.step was 1. We can change the collections.step.
    for (i in 10 downTo 1 step 2) {
        println(i)
    }
}