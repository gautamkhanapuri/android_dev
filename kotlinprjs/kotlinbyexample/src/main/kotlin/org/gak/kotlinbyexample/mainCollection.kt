package org.gak.kotlinbyexample

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
}

// Lists can be either mutable (MutableList) or read-only (List).
// MutableList<T> - Mutable list
// List<T> - Immutable List

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
            i -> println("Item value: $i")
    }
}