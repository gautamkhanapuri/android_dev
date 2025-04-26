package org.gak.kotlinbyexample

// In Kotlin the object keyword is used to obtain a data type with a single implementation.
object DoAuth {                                                 //1
    fun takeParams(username: String, password: String) {        //2
        println("input Auth parameters = $username:$password")
    }
}

// An object declaration inside a class creates a companion object.
// Syntactically it's similar to the static methods in Java.
// Invoke object members using its class name as a qualifier
class BigBen {                                  //1
    companion object Bonger {                   //2
        fun getBongs(nTimes: Int) {             //3
            for (i in 1 .. nTimes) {
                print("BONG ")
            }
        }
    }
}


fun mainObject(){
    println("**** Object and Companion Classes ***")

    DoAuth.takeParams("foo", "qwerty")                          //3
    BigBen.getBongs(12)
}