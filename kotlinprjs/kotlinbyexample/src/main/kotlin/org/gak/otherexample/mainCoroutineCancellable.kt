package org.gak.otherexample

import kotlinx.coroutines.*

// Coroutine cancellation is cooperative. A coroutine code has to cooperate
//   to be cancellable. All the suspending functions in kotlinx.coroutines are
//   cancellable.
// They check for cancellation of coroutine and throw CancellationException when
//   cancelled. However, if a coroutine is working in a computation and does not
//   check for cancellation, then it cannot be cancelled
// The problem of non-cancellation can be observed by catching a CancellationException
//   and not rethrowing it:
// While catching Exception is an anti-pattern, this issue will show when using the runCatching
//   function, which does not rethrow CancellationException

fun mainCoroutineCancellableException() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(5) { i ->
            try {
                // print a message twice a second
                println("job: I'm sleeping $i ...")
                delay(500)
            } catch (e: Exception) {
                // log the exception
                println(e)

            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    // it continues to print "I'm sleeping" even after cancellation until the j
    //   ob completes by itself after five iterations.
    println("main: Now I can quit.")
}


fun mainCoroutineCancellableComputation() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // computation loop, just wastes CPU
            // print a message twice a second
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