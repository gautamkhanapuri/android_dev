package org.gak.otherexample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun simple(): Sequence<Int> = sequence { // sequence builder
    for (i in 1..3) {
        Thread.sleep(100) // pretend we are computing it
        yield(i) // yield next value
    }
}

fun mainSequence() {
    simple().forEach { value -> println(value) }
}

suspend fun simpleSuspend(): List<Int> {
    delay(1000) // pretend we are doing something asynchronous here
    return listOf(1, 2, 3)
}

fun mainSuspend() = runBlocking<Unit> {
    simpleSuspend().forEach { value -> println(value) }
    println("simpleSuspend calling ==> ")
}

fun simpleFlow(): Flow<Int> = flow { // flow builder
    for (i in 1..3) {
        delay(100) // pretend we are doing something useful here
        emit(i) // emit next value
    }
}

fun mainFlow() = runBlocking<Unit> {
    // Launch a concurrent coroutine to check if the main thread is blocked
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(100)
        }
    }
    // Collect the flow
    simpleFlow().collect { value -> println(value) }
}


fun simpleFlowCollect(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

fun mainFlowCollect() = runBlocking<Unit> {
    println("Calling simpleFlowCollect function...")
    val flow = simpleFlowCollect()
    println("Calling collect...")
    flow.collect { value -> println(value) }
    println("Calling collect again...")
    flow.collect { value -> println(value) }
}

fun simpleFlowCancel(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        println("Emitting $i")
        emit(i)
    }
}

fun mainFlowCancel() = runBlocking<Unit> {
    withTimeoutOrNull(250) { // Timeout after 250ms
        simpleFlowCancel().collect { value -> println(value) }
    }
    println("Done")
}

fun mainFlowBuilder() = runBlocking<Unit> {
    // Convert an integer range to a flow
    (1..3).asFlow().collect { value -> println(value) }
}