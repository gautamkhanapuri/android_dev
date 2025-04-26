package org.gak.kotlinbyexample

fun mainArrays() {
    println("**** Arrays ***")
    var riversArray = arrayOf("Nile", "Amazon", "Yangtze")
    println("printing with forEach on arrays")
    riversArray.forEach { print("$it, ") }
    println()
    println("Adding to an array")
    riversArray += "Thales"
    riversArray.forEach { print("$it, ") }
    println()
    println("printing with joinToString")
    println(riversArray.joinToString(", "))
}