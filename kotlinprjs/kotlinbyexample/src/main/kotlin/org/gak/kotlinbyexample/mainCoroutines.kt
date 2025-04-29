package org.gak.kotlinbyexample

// Kotlin provides only minimal low-level APIs in its standard library to enable other
//  libraries to utilize coroutines. Unlike many other languages with similar capabilities,
//  async and await are not keywords in Kotlin and are not even part of its standard library.
// Moreover, Kotlin's concept of suspending function provides a safer and less error-prone 
//  abstraction for asynchronous operations than futures and promises.
import kotlinx.coroutines.*

fun mainCoroutine() {
    println("**** Coroutines ***")
    mainCoroutineBasic()
    mainCoroutineFunctionRefactor()
    mainCoroutineCoroutineScope()
    mainCoroutineCoroutineScopeConcurrent()
    mainCoroutineJoin()
    mainCoroutinePerformance()
}

fun mainCoroutineBasic() = runBlocking { // this: CoroutineScope
    println("**** Coroutines Basic ***")

    launch { // launch a new coroutine and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello") // main coroutine continues while a previous one is delayed
}

fun mainCoroutineFunctionRefactor() = runBlocking { // this: CoroutineScope
    println("**** Coroutines Refactored as suspend function ***")
    launch { doWorld() }
    println("Hello")
}

// this is a suspending function
suspend fun doWorld() {
    delay(1000L) //yield
    println("World!")
}

// Similarities between runBlocking and coroutineScope builders
//   may look similar because they both wait for their body and all
//   its children to complete.
//  Main difference between runBlocking and coroutineScope is that
//   runBlocking method blocks the current thread for waiting,
//   coroutineScope just suspends, releasing the underlying thread for other usages.
// Because of that difference,
//   runBlocking is a regular function
//   coroutineScope is a suspending function.

fun mainCoroutineCoroutineScope() = runBlocking {
    doWorldCoroutineScope()
}

suspend fun doWorldCoroutineScope() = coroutineScope {  // this: CoroutineScope
    println("*** runBlocking and coroutineScope ***")
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}

// Sequentially executes doWorld followed by "Done"
fun mainCoroutineCoroutineScopeConcurrent() = runBlocking {
    doWorldCoroutineScopeConcurrent()
    println("Done")
}

// Concurrently executes both sections
suspend fun doWorldCoroutineScopeConcurrent() = coroutineScope { // this: CoroutineScope
    println("*** runBlocking and coroutineScope Concurrent ***")
    launch {
        delay(2000L)
        println("World 2")
    }
    launch {
        delay(1000L)
        println("World 1")
    }
    println("Hello")
}


fun mainCoroutineJoin() = runBlocking {
    println("*** runBlocking with join ***")

    val job = launch { // launch a new coroutine and keep a reference to its Job
        delay(1000L)
        println("World!")
    }
    println("Hello")
    job.join() // wait until child coroutine completes
    println("Done")
}

// Coroutines are less resource-intensive than JVM threads.
//   Code that exhausts the JVM's available memory when using threads
//   can be expressed using coroutines without hitting resource limits.
fun mainCoroutinePerformance() = runBlocking {
    println("*** coroutine performance ***")
    repeat(50_000) {  i ->// launch a lot of coroutines
        launch {
            delay(1000L)
            if ( i % 1000 == 0) {
                val k: Int = i / 1000
                print("\n$k")
            }
            print(".")
        }
    }
    println("Done")
}