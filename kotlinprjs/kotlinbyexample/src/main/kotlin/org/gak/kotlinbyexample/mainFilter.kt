package org.gak.kotlinbyexample

fun mainFilter() {
    println("**** Extensions ***")

    val numbers = listOf(1, -2, 3, -4, 5, -6)      // 1
    println("Number list inferred ==> $numbers")

    val ps1: (Int) -> Boolean = { x: Int -> x>0 }
    val ps2: (Int) -> Boolean = { x -> x>0 }
    val pas3 = { x: Int -> x>0 }
    // val ps4 = { x -> x>0 }  // Cannot infer what is the type of 'x'
    val ps5: (Int) -> Boolean = { it > 0}
    val sq = { x: Int -> x * x}
    // Method reference
    val upper: (String) -> String = String::uppercase

    val ps: (x: Int) -> Boolean = { x: Int -> x>0 }
    val p1 = numbers.filter(ps)
    println("Filtered numbers filter expression x>0 ==> $p1")
    val positives = numbers.filter { x -> x > 0 }  // 2
    println("Filtered positive numbers lambda expression (x -> x>0) ==> $positives")

    val negatives = numbers.filter { it < 0 }
    println("Filtered negative number lambda with 'it < 0' ==> $negatives")

    val doubled = numbers.map { x -> x * 2 } // 2
    println("Map function (x -> x*2) applied to a list ==> $doubled")

    val tripled = numbers.map { it * 3 }
    println("Map function (it * 3) applied to a list with 'it' variable ==> $tripled")

    val anyNegative = numbers.any { it < 0 } // 2
    println("Any function (it < 0) applied to a list ==> $anyNegative")

    val anyGT6 = numbers.any { it > 6 }
    println("Any function (it > 6) with different condition to a list ==> $anyGT6")

    val allEven = numbers.all { it % 2 == 0 }            // 2
    println("All function ( it % 2) to a list ==> $allEven")

    val allLess6 = numbers.all { it < 6 }
    println("All function (it < 6) to a list ==> $allLess6")

    val noneEven = numbers.none { it % 2 == 1 }           // 2
    println("None function (it % 2 == 1) to a list ==>  $noneEven")

    val NoneGreater6 = numbers.none { it > 6 }
    println("None function (it > 6) to a list ==> $NoneGreater6")

    val totalCount = numbers.count()                     // 2
    println("Count items in a list ==> $totalCount")
    val evenCount = numbers.count { it % 2 == 0 }
    println("Even number count (it % 2 == 0) in a list ==> $evenCount")

    val evenOdd = numbers.partition { it % 2 == 0 }           // 2
    println("Partition function (it % 2 == 0) to a list ==> $evenOdd")
    val (positivs, negativs) = numbers.partition { it > 0 }
    println("Partition function (it > 0) and separated positives as return values ==> $positivs")
    println("Partition function (it > 0) and separated negatives as return values ==> $negativs")

    val map = mapOf("key" to 42).withDefault { key:String  -> 0 }
    val value1 = map["key"]                                     // 1
    val value2 = map["key2"]
    val mapWithDefault = map.withDefault { k -> k.length }
    val value4 = mapWithDefault.getValue("key2")


    val A = listOf("a", "b", "c")                  // 1
    val B = listOf(1, 2, 3, 4)                     // 1

    val resultPairs = A zip B // 2
    print(resultPairs)
    val resultReduce = A.zip(B) { a, b -> "$a$b" } // 3
    println("Zip applied to two lists ==> $resultReduce")

    val list = listOf(0, 10, 20)
    println("List get with valid index ==> ${list.getOrElse(1) { 42 }}")    // 1
    println("List get with invalid index ==> ${list.getOrElse(10) { 42 }}")   // 2


    var map1 = mutableMapOf<String, Int?>()
    println("Map get with invalid key ==> ${map1.getOrElse("x") { 1 }}")       // 1

    map1["x"] = 3
    println("Map get with valid key ==> ${map1.getOrElse("x") { 1 }}")       // 2

    map1["x"] = null
    println("Map get with null value for a key ==> ${map.getOrElse("x") { 1 }}")       // 3

}