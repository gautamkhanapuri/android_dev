package org.gak.kotlinbyexample

fun mainIf() {
    println("**** If, Range ***")
    val x1 = 10
    val y1 = 9
    if (x1 in 1..y1+1) {
        println("fits in range")
    }

    val list = listOf("a", "b", "c")

    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range, too")
    }

    // Kotlin uses == for structural comparison and === for referential comparison.
    val authors = setOf("Shakespeare", "Hemingway", "Twain")
    val writers = setOf("Twain", "Shakespeare", "Hemingway")

    println("Structure Comparison calls equals 'authors == writers' ==> ${authors == writers}")   // 1
    println("Reference Comparison checks referenceId 'authors === writers' ==> ${authors === writers}")  // 2

}