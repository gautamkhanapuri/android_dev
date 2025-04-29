package org.gak.otherexample

import kotlinx.coroutines.*

// The most obvious practical reason to cancel execution of a coroutine is because its
//   execution time has exceeded some timeout with a ready to use withTimeout context
fun mainCoroutineCancellableTimeout() = runBlocking {
    withTimeout(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
}


// Since cancellation is a TimeoutCancellationException thrown by withTimeout, all resources
//   are closed in the usual way. Wrap the code with timeout in a
//   try {...} catch (e: TimeoutCancellationException) {...} block to do some additional action
//   specifically on any kind of timeout or use the withTimeoutOrNull function that is similar
//   to withTimeout but returns null on timeout instead of throwing an exception
fun mainCoroutineCancellableTimeoutNull() = runBlocking {
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
        "Done" // will get cancelled before it produces this result
    }
    println("Result is $result")
}