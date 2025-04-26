package org.gak.kotlinbyexample

fun mainStream() {
    println("**** Stream API ***")
    val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
    fruits
        .filter { it.startsWith("a") }
        .sortedBy { it }
        .map { it.uppercase() }
        .forEach { print(it + ", ") }
    println()
}