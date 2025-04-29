package org.gak.otherexample

import kotlinx.coroutines.*

// An approach to make computation code cancellable by explicitly checking the
//   cancellation status using isActive
// Now this computational loop is cancelled, isActive is an extension property
//   available inside the coroutine via the CoroutineScope object

fun mainCoroutineCancellableIsActive() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // cancellable computation loop
            // prints a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}