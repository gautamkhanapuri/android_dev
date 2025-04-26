import java.util.*

fun mainConstructingElements() {

}

fun constructFromElements() {
    // The type of collection will be understood automatically by the compiler
    // if the elements are declared immediately. But if it is only a variable declaration, the type
    // has to mentioned
    val l = listOf("1", "2", "3", "four", "five")
    val emptySet = mutableSetOf<String>()
    println(l)
}

fun initializeMap(): Unit {
    //    Note that the to notation creates a short-living Pair object, so it's recommended that you use
    //    it only if performance isn't critical. To avoid excessive memory usage, use alternative ways.
    //    For example, you can create a mutable map and populate it using the write operations. The
    //    apply() function can help to keep the initialization fluent here.
    val numbersMap: MutableMap<String, String> =
        mutableMapOf<String, String>().apply { this["one"] = "1"; this["two"] = "2" }

//    Apply is a Kotlin scope function. It runs the block {} on the object itself and then returns the object.
//    Inside apply {}, this refers to the Map. So you are inserting key-value pairs into it.
//    Semicolons are optional in Kotlin. Only needed if you have multiple statements on the same line.
}

fun withBuilder() {
    val map = buildMap<Int, String> {
        put(1, "one")
        put(2, "two")
        put(3, "three")
    }
    //same as
    val map1: Map<Int, String> = buildMap {
        put(1, "one")
        put(2, "two")
        put(3, "three")
    }
}

fun emptyCollection() {
    // they return read only collections
    val emptySet = emptySet<String>()
    val empty = emptyList<String>()
    val emptyMap: Map<Int, String> = emptyMap<Int, String>()
}

fun initializerForLists() {
    val doubled: List<Int> = List(3) { i -> (i * i) } // last parameter is a function so it can
    // be moved out of the ().
    println(doubled)
    val linkedList = LinkedList<String>(listOf("one", "two", "three"))
    val presizedSet = HashSet<Int>(32)
}

fun copying() {
//    Collection copying functions from the standard library create shallow copy collections with references to the same elements.
    val alice: MutableList<String> = mutableListOf("one", "two", "three")
    val sourceList: List<String> = alice.toList()
    alice.add("four")
    println(alice)
    println(sourceList)

    val sourceList1 = mutableListOf(1, 2, 3)
    val copySet = sourceList1.toMutableSet()
    copySet.add(3)
    copySet.add(4)
    println(copySet)

    // Alternatively, you can create new references to the same collection instance. New references
    // are created when you initialize a collection variable with an existing collection. So, when
    // the collection instance is altered through a reference, the changes are reflected in all its references.
    val sl = mutableListOf(1, 2, 3)
    val referenceList = sl
    referenceList.add(4)
    println("Source size: ${sl.size}")

    // Collection initialization can be used for restricting mutability. For example, if you create
    // a List reference to a MutableList, the compiler will produce errors if you try to modify the
    // collection through this reference.
    val sl1 = mutableListOf(1, 2, 3)
    val rl: List<Int> = sl1
    //rl.add(4)            //compilation error
    sl1.add(4)
    println(rl) // shows the current state of sl1}
}