package org.gak.otherexample

import kotlinx.coroutines.*
import kotlin.system.*

fun mainCoroutineSequential() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("Completed in $time ms")
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

// Invocations of independent functions doSomethingUsefulOne and doSomethingUsefulTwo
//   can be done concurrently to get results faster, by using async.
// async is like launch. It starts a separate coroutine which is a light-weight thread
//   that works concurrently with all the other coroutines. The difference is that launch
//   returns a Job and does not carry any resulting value, while async returns
//   a Deferred — a light-weight non-blocking future that represents a promise to provide
//   a result later. The method .await() can be called on a deferred value to get its
//   eventual result, but Deferred is also a Job, so if can be cancelled if required.

fun mainCoroutineAsync() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

// async can be made lazy by setting its start parameter to CoroutineStart.LAZY.
//   In this mode it only starts the coroutine when its result is required by await, or if
//   its Job's start function is invoked
fun mainCoroutineAsyncLazy() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // some computation
        one.start() // start the first one
        two.start() // start the second one
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

// The Concurrency async example can be combined into a function that runs d
//   oSomethingUsefulOne and doSomethingUsefulTwo concurrently and returns their combined
//   results. Since async is a CoroutineScope extension, using the coroutineScope function to
//   provide the necessary scope
// This way, if something goes wrong inside the code of the concurrentSum function, and it
//   throws an exception, all the coroutines that were launched in its scope will be cancelled.

suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

fun mainCoroutineAsyncConcurrent() = runBlocking<Unit> {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}

// Cancellation is always propagated through coroutines hierarchy
fun mainCoroutineAsyncConcurrentCancellation() = runBlocking<Unit> {
    try {
        failedConcurrentSum()
    } catch(e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}

suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE) // Emulates very long computation
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}