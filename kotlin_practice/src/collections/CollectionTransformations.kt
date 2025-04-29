package collections// map
// zip
// associate
// flatten
// String representation
// reduce

fun mainTransformations() {

}

fun mappingExamples() {
    val numbers = setOf(1, 2, 3)
    println(numbers.map { it * 3 }) // .map() is a function which applies the given lambda function to all
    // elements in the collection.
    println(numbers.mapIndexed { idx, value -> value * idx })

    val num = listOf(1, 2, 3)
    println(num.mapNotNull { if (it == 2) null else it * 3})
    println(num.mapIndexedNotNull { idx, value -> if (idx == 0) null else value * idx })

    val numMap: Map<Int, String> = buildMap {
        put(1, "1")
        put(2, "2")
        put(3, "3")
        put(4, "4")
    }
    // You are allowed to transform only the keys, while keeping values the same.
    println(numMap.mapKeys { it.key * 2})
    // You are allowed to transform only the values, while keeping keys the same.
    println(numMap.mapValues { it.value.toInt() * it.key})

    // You can modify both simulataneously, but it is error prone
    val newMap = numMap.map { (k, v) -> k * 2 to v.toInt() * 2 }.toMap()
    println(newMap)
}

fun zippingExamples() {
    // the size of the zip() is the smaller size. The last elements of the larger collection are not
    // included in the result.
    // zip can also be called in the infix form.
    val colors = listOf("red", "brown", "grey")
    val animals = listOf("fox", "bear", "wolf")
    println(colors zip animals)

    val twoAnimals = listOf("fox", "bear")
    println(colors.zip(twoAnimals))
    // these two .zip() methods are method overloads.
    println(colors.zip(animals) {color, animal -> "The ${animal.replaceFirstChar { it.uppercase() }} is $color"})
}

fun unzippingExamples() {
    val numPairs: List<Pair<String, Int>> = listOf("one" to 1, "two" to 2, "three" to 3, "four" to 4)
    val (list1, list2) = numPairs.unzip()
    println(list1)
    println(list2)
}

fun associationExamples() {
    // this transformation creates maps
    // This func operates on the values in something like a list and the result of the operation is
    // assigned as the value of the key.
    // If two elements are equal, the last one remains in the map.
    val numbers = listOf("one", "two", "three", "four")
    println(numbers.associateWith { it.length })

    // associateBy()
    //  It takes a function that returns a key based on an element's value. If two elements' keys
    //  are equal, only the last one remains in the map.
    val num = listOf("one", "two", "three", "four")
    println(num.associateBy { it.first().uppercaseChar() })
    println(num.associateBy(keySelector = { it.first().uppercaseChar() }, valueTransform = { it.length }))

    // associate()
    // This is when both key and value are generated from the same element of a list.
    // It takes a lambda function that returns a Pair object.
    // Not extremely performant because it creates a temporary Pair object.

    val names = listOf("Alice Adams", "Brian Brown", "Clara Campbell")
    println(names.associate { it.split(" ")[0] to it.split(" ")[1] })
    // same thing.
    println(names.associate { val (firstName, secondName) = it.split(" "); Pair(firstName, secondName) })
    // Kotlin lambdas can return values implicitly without needing the return keyword, as long as
    // there’s an expression as the last line of the lambda.
    // if your lambda has multiple statements, you should end the lambda with an expression that is
    // the result of the last statement.
}

fun flattenExamples() {
    val numberSets = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(1, 2))
    println(numberSets.flatten())
}

fun stringRepresentation() {
    val numbers = listOf("one", "two", "three", "four")

    println(numbers)
    // joinToString() builds a single String from the collection elements based on the provided arguments.
    println(numbers.joinToString())

    val listString = StringBuffer("The list of numbers: ")
    // joinTo() does the same but appends the result to the given Appendable object.
    numbers.joinTo(listString)
    println(listString)
}