package collections

fun main() {
    val aList: List<String> = listOf("A", "B", "C")
    val mList: MutableList<String> = mutableListOf("A", "B", "C")

    val aSet: Set<String> = setOf("A", "B", "C", "D", "B")
    val mSet: MutableSet<String> = mutableSetOf("A", "B", "X", "Y", "X", "X")

    printAll(aList)
    printAll(mList)
    printAll(aSet)
    printAll(mSet)

    mList.add("Added")
    mList.add(0, "Add 2")
    mList[1] = "Update"

    mSet.add("Magic")

    // aSet and aList donot have any updating methods itself. They only have getter methods.

    val aMap: Map<String, Int> = mapOf(
        "Mike" to 1,
        "Ben" to 2,
        "Mary" to 3,
    )
    print(aMap)
    val mMap: MutableMap<String, Int> = mutableMapOf(
        "Mike" to 4,
        "Ben" to 5,
        "Mary" to 6,
        "Mike" to 4,
        "Brave" to 5,
        "Mary" to 99
    )
    print(mMap)
    println(buildSentence("Kotlin", "makes", "coding", "fun"))

}

private fun printAll(strings: Collection<String>) {
    for (string in strings) {
        print("$string ")
    }
    println()
    // The Here, it is Kotlin’s default name for a single parameter in a lambda when you don’t explicitly name it.
    println("Another way:")
    strings.forEach {
        print("$it ")
    }
    println()
}

fun buildSentence(vararg words: String): String {
    return words.joinToString(separator = " ") + "."
}

fun practiceMap(): Unit {
    val l: List<String> = listOf("1", "2", "3", "four", "five")
    println(l)

    // A higher order func that converts strings to int or null. the mapNotNull function is the higher order map function that takes in only non null values and returns the new map.
    val nums: List<Int> = l.mapNotNull {
        it.toIntOrNull()
    }
    printAll(l)
    println(nums)

    l.mapIndexed {i:Int, s: String ->
        "$i: $s"
    }
}

fun practiceNullSafeOperations(name: String?): Unit {
    // val name: String? = null

// Safe call operator
    println(name?.length)  // prints "null", no crash. But you must give it access. name.length will throw error. specify ? after name to ensure access.

// Elvis operator: It returns a default value if the left side is null
    val len = name?.length ?: 0 // if null, use 0. Not mandatory to use. If no elvis operator, it simply returns null.

// Not-null assertion
    println(name!!.length)  // throws if name is null
}

fun practiceFiltering(): Unit {
    val l: List<String> = listOf("1", "2", "3", "four", "five")
    val l_true: List<String> = l.filter {
        it.length < 2
    }

    val l_false: List<String> = l.filterNot {
        it.length < 2
    }

    val (l_t, l_f) = l.partition {
        it.length < 2
    }

//    val l_t: List<String>
//    val l_f: List<String>
//    l_t, l_f = l.partition { it.length < 2 }  Not valid in Kotlin
//    This line is invalid because Kotlin doesn’t support destructuring assignment after variable declaration
}

fun practiceArray(): Unit {
    val arr = arrayOf(1, 2, 3)
    val arr1 = Array(5) { i -> i * 2 }  // [0, 2, 4, 6, 8]

}

/**
 * This is an extension function for List<String>.
 *
 * It adds a new function to the List<String> class — without modifying it!
 */
fun List<String>.getShortWordsTo(shortWords: MutableList<String>, maxLength: Int) {
    val l: List<String> = listOf("1", "2", "3", "four", "five")
    l.filterTo(shortWords) { it.length <= maxLength }
    // throwing away the articles
    val articles = setOf("a", "A", "an", "An", "the", "The")
    shortWords -= articles
}