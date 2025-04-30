package collections

fun mainIterators() {

}

fun createAnIterator(): Unit {
    val numbers = listOf("one", "two", "three", "four")
    val numbersIterator = numbers.iterator()
    while (numbersIterator.hasNext()) {
        println(numbersIterator.next())
        // one
        // two
        // three
        // four
    }
}

fun useForEachFunction() {
    val numbers = listOf("one", "two", "three", "four")
    numbers.forEach(::println)
    numbers.forEach { it ->
        println(it)
    }
}

fun iteratingBackwards() {
    val numbers = listOf("one", "two", "three", "four")
    val listIterator = numbers.listIterator()
    while (listIterator.hasNext()) listIterator.next()
    println("Iterating backwards:")
// Iterating backwards:
    while (listIterator.hasPrevious()) {
        print("Index: ${listIterator.previousIndex()}")
        println(", value: ${listIterator.previous()}")
        // Index: 3, value: four
        // Index: 2, value: three
        // Index: 1, value: two
        // Index: 0, value: one
    }
}

fun removingFromMutableIterators() {
    val numbers = mutableListOf("one", "two", "three", "four")
    val numbersIterator = numbers.listIterator()

    numbersIterator.next()
    numbersIterator.remove()
    //    println("After removal: $numbers")
    // After removal: [two, three, four]
}

fun addingToMutableIterators() {
    val numbers = mutableListOf("one", "four", "four")
    val mutableListIterator = numbers.listIterator()

    mutableListIterator.next()
    mutableListIterator.add("two")
    println(numbers)
// [one, two, four, four]
    mutableListIterator.next()
    mutableListIterator.set("three")
    println(numbers)
// [one, two, three, four]
}