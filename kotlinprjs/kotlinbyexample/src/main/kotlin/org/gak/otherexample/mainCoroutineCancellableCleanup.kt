package org.gak.otherexample

import kotlinx.coroutines.*
// Cancellable suspending functions throw CancellationException on cancellation, which can be
// handled with the finally block.

fun mainCoroutineCancellableCleanup() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            println("job: I'm running finally")
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

// Any attempt to use a suspending function in the finally block of the previous example causes
//   CancellationException, because the coroutine running this code is cancelled.
// However to invoke a suspend function in a cancelled coroutine, wrap the corresponding code in
//   withContext(NonCancellable) {...} using withContext function and NonCancellable context

fun mainCoroutineCancellableCleanupWithContext() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}