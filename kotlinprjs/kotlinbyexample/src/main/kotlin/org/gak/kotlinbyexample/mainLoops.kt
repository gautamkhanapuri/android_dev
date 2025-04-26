package org.gak.kotlinbyexample

fun mainLoops() {
    println("**** Loops ***")
    println(" Inclusive loops with i..j  ")
    for (i in 1..5) {
        print("i = $i, ")
    }
    println()

    println(" Decreasing loops with downTo")
    for (i in 6 downTo 0 step 2) {
        print("" + i + ", ")
    }
    println()

    for (x in 9 downTo 0 step 3) {
        print(x)
        print(", ")
    }
    println()

    println(" Increasing loops with step")
    for (x in 1..10 step 2) {
        print(x)
        print(", ")
    }
    println()


    println(" Array Indices and their values")
    val riversArray = arrayOf("Nile", "Amazon", "Yangtze")
    for (v in riversArray.indices) {
        println("v=$v, ==> " + riversArray[v])
    }

    println(" Array values ONLY")
    for (v in riversArray) {
        print("$v, ")
    }
    println()


    println(" List iteration values ONLY")
    val items = listOf("apple", "banana", "kiwifruit")
    for (item in items) {
        print("$item, ")
    }
    println()
    println("Using List's joinToString ==>  " + items.joinToString(","))

    println("List for loop with indices")
    for (index in items.indices) {
        println("item at $index ==> ${items[index]}")
    }

    println("List while loop with an index")
    var index = 0
    while (index < items.size) {
        println("item at $index ==> ${items[index]}")
        index++
    }

    println("Range print loop")
    for (xy in 1..5) {
        print("$xy, ")
    }
    println()
}