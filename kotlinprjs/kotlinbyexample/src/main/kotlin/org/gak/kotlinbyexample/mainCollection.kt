package org.gak.kotlinbyexample

// Ability to group data into structures for processing are collections
// Kotlin has the following collections for grouping items:
//  Lists      ---> Ordered collections of Items, List<T>, MutableList<T>
//  Sets       ---> Unique Unordered collections of Items, Set<T>, MutableSet<T>
//  Maps       ---> Sets of key-value pairs, with unique keys and have mapped value, Map<K,V>, MutableMap<K,V>
// Casting is create a read-only view of a mutable collection to its immutable form
//  val shapes: MutableList<String> = mutableListOf("triangle", "square", "circle")
//  val shapesLocked: List<String> = shapes
//  val fruit: MutableSet<String> = mutableSetOf("apple", "banana", "cherry", "cherry")
//  val fruitLocked: Set<String> = fruit


fun mainCollection() {
    println("**** Collections ***")

    val pair = "Ferrari" to "Katrina"
    println("Pair Data ==> $pair")

    // Read only list
    val list1 = listOf("a", "b", "c")
    // read only map
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)
    // Mutable map
    var map1 = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
    map1["key"] = 5
    for ((k, v) in map1) {
        println("$k -> $v")
    }

    val authors = setOf("Shakespeare", "Hemingway", "Twain")
    val writers = setOf("Twain", "Shakespeare", "Hemingway")

    println(authors == writers)   // true
    println(authors === writers)  // false

    val vals: List<Int> = kotlinLists()
    println("Mutable List with reference of Immutable list ==> $vals")

    readWriteLists()

    mainLists()
    mainSet()
    mainMap()
}

// Lists can be either mutable (MutableList) or read-only (List).
// MutableList<T> - Mutable list
// List<T> - Immutable List
fun mainLists() {
    val mList: MutableList<String> = mutableListOf("a", "b", "c")
    println("MutableList<String> ==> $mList")
    val imList: List<String> = listOf("a", "b", "c")
    println("List<String> ==> $imList")
    val mCopyList: List<String> = mList
    // val imCopyList: MutableList<String> = imList // Type mismatch compilation error
    // Once it is of type List<String> it cannot be converted back to MutableList<String>
    // A programmer needs to hold a reference of MutableList<String>
    // val imCopyList: MutableList<String> = mCopyList
    println("Print MutableList<String> casted as List<String> ==> $mCopyList")
    mList.add("d")
    println("Adding to MutableList<String> which also created as mutableListOf() ==> $mList")
    // imList.add("d")  // List<String> does not add as it is Read-Only Interface
    // mCopyList.add("d") // Original MutableList<String> assigned to List<String>, so no add
}

fun kotlinLists(): MutableList<Int> {
    var iList: MutableList<Int> = mutableListOf(1,2,3,4,5)
    iList.add(6)
    return iList
}

fun readWriteLists() {
    val systemUsers: MutableList<Int> = mutableListOf(1, 2, 3)        // 1
    val users: List<Int> = listOf(1, 2, 3)        // 1
    systemUsers.add(4)
    // Compile error as it is readonly
    // users.add(4)
    // To prevent unwanted modifications, obtain read-only views of mutable lists by casting
    //  them to List
    val readCopy: List<Int> = systemUsers
    // Compile error as this is variable is pointing as read only
    // readCopy.add(5)

    systemUsers.forEach {                                     // 7
            i -> println("List Item value ==> $i")
    }
}

// A set is an unordered collection with no duplicates.
// Immutable Set<String> ==> setOf()
// Mutable   MutableSet<String> ==> mutableSetOf().
// A read-only view of MutableSet can be obtained by casting it to Set
fun mainSet() {
    val mIssues: MutableSet<String> = mutableSetOf("Bug1", "Bug2", "Bug3")
    val imIssues: Set<String> = mutableSetOf("Bug4", "Bug5", "Bug6")
    println("Mutable MutableSet<String> ==> $mIssues")
    println("ReadOnly Set<String> ==> $imIssues")
    val mCopySet: Set<String> = mIssues
    // mCopySet.add("Bug7") // Compilation error as it is readonly set.
    // val imCopySet: MutableSet<String> = imIssues // Type mismatch compilation error
    println("Print MutableSet<String> casted as Set<String> ==> $mCopySet")
    println("Adding to MutableSet<String> true if success ==> ${mIssues.add("Bug7")}")
    println("Adding to MutableSet<String> fail if exists ==> ${mIssues.add("Bug7")}")
    mIssues.forEach {                                     // 7
            i -> println("Set Item value ==> $i")
    }
}

// A map is a collection of key/value pairs, where each key is unique and
//   is used to retrieve the corresponding value.
//   Mutable MutableMap<Int, Int> ==> mutableMapOf()
//   Immutable Map<Int, Int> ==> MapOf()
//   Using the 'to' infix function to link key and value of each pair.
//   A read-only view of a mutable map can be obtained by casting it to Map
fun mainMap() {
    val mMap: MutableMap<Int, Int> = mutableMapOf(1 to 101, 2 to 102, 3 to 103)
    val imMap: Map<Int, Int> = mapOf(10 to 101, 20 to 102, 30 to 103)
    println("Mutable MutableMap<Int, Int> ==> $mMap")
    println("ReadOnly Map<Int, Int> ==> $imMap")
    mMap.put(4, 104)
    println("Updated Mutable MutableMap<Int, Int> ==> $mMap")
    val mCopyMap: Map<Int, Int> = mMap
    // mCopyMap.put(5, 105) // Compilation error as it is readonly Map.
    println("Print MutableSet<Int,Int> casted as Map<Int, Int> ==> $mCopyMap")
    print("Map iteration print using foreach ==>")
    mMap.forEach {                                                              // 5
            k, v -> print("key --> $k: value --> $v, ")
    }
    println()

    val map = mapOf("Alice" to 21, "Bob" to 25)
    for ((name, age) in map) {                                      // 2
        println("$name is $age years old")
    }

    val (num, name) = Pair(1, "one")             // 2

    println("num = $num, name = $name")
}

class Pair<K, V>(val first: K, val second: V) {  // 1
    operator fun component1(): K {
        return first
    }

    operator fun component2(): V {
        return second
    }
}
