package org.gak.kotlinbyexample

fun getEvalStr(state: State): String {
    val message = when (state) {                      // 3
        State.IDLE -> "It's idle"
        State.RUNNING -> "It's running"
        State.FINISHED -> "It's finished"
    }
    return message
}

fun mainEnum() {
    println("**** Enum ***")

    val message = getEvalStr(State.RUNNING)                         // 2
//    val message = when (state) {                      // 3
//        State.IDLE -> "It's idle"
//        State.RUNNING -> "It's running"
//        State.FINISHED -> "It's finished"
//    }
    println("Enum State ==> $message")

    val red = Color.RED
    println(red)                                      // 4
    println(red.containsRed())                        // 5
    println(Color.BLUE.containsRed())                 // 6
    println(Color.YELLOW.containsRed())               // 7
}

// simple enum class with three enum constants
enum class State {
    IDLE, RUNNING, FINISHED                           // 1
}

// Enums can contain properties and methods like other classes,
// separated from the list of enum constants by a semicolon.
enum class Color(val rgb: Int) {                      // 1
    RED(0xFF0000),                                    // 2
    GREEN(0x00FF00),
    BLUE(0x0000FF),
    YELLOW(0xFFFF00);

    fun containsRed() = (this.rgb and 0xFF0000 != 0)  // 3
}