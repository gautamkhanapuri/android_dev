package org.gak.kotlinbyexample

fun mainWhen() {
    println("**** When ***")

    val items = listOf("apple", "banana", "kiwifruit")
    when {
        "orange" in items -> println("juicy")
        "apple" in items -> println("apple is fine too")
    }

    fun describe(obj: Any): String =
        when (obj) {
            1          -> "One"
            "Hello"    -> "Greeting"
            is Long    -> "Long"
            !is String -> "Not a string"
            else       -> "Unknown"
        }

    println("when expression with function expression")
    println("describe(1) ==> ${describe(1)}")
    println("describe(\"Hello\") ==> " + describe("Hello"))
    println("describe(1000L) ==> " + describe(1000L))
    println("describe(2) ==> " + describe(2))
    println("describe(\"other\") ==> " + describe("other"))

}