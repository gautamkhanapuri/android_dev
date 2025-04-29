package org.gak.otherexample

import kotlinx.coroutines.*

// For long-running application, fine-grained control on your background coroutines
//   is required.The launch function returns a Job that can be used to cancel
//   the running coroutine

fun mainCoroutineCancel() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    job.join() // waits for job's completion
    println("main: Now I can quit.")
    // There is also a Job extension function cancelAndJoin that combines
    //   cancel and join invocations.
}