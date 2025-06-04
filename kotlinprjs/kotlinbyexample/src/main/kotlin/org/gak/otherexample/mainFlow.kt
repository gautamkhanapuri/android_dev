package org.gak.otherexample

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// Flows represent asynchronous data stream
// Hot Flows - Data production starts irrespective of being consumed.
//   send  ===>    ***Channel***  ====> receive
// Cold Flows - Data production starts when at-least one consumer is consuming
//   emit  ===>    *************  ====> collect


// sharedFlow is hot flow
// Broadcast listeners - can be one or many

fun  checkSharedFLow() {
    var sharedFlow = MutableSharedFlow<Int>(replay = 4)
    runBlocking {
        repeat(10) {
            delay(500L)
            println("$it")
            sharedFlow.emit(it)
        }
        println("Done producing")

//        sharedFlow.collectLatest {
//            println("Collected: $it")
//        }

//        launch {
//            sharedFlow.collectLatest {
//                println("Collected: $it")
//            }
//        }

        println("Done")

        flow {
            repeat(10) {
                delay(500)
                emit(it)
            }
        }.collectLatest {
            value ->
            println("Collecting $value")
            delay(100)
        } // Emulate work
    }
}


fun  checkStateFLow() {
    var stateFlow = MutableStateFlow(0)
    runBlocking {
        launch {
            repeat(10) {
                delay(500L)
                println("Produced $it")
                stateFlow.emit(it)
            }
        }
        println("Done producing")
        // stateFlow.collect()

//        stateFlow.collectLatest {
//            println("Collected: $it")
//        }
    }
}

fun checkFlow() {
    runBlocking {
        val pr = producer()
        try {
            pr.map {
                    delay(500L)
                    println("Mapping $it ==> ${Thread.currentThread().name}")
                    it * 2 - 1
                }
                .filter {
                    delay(500L)
                    println("Filter  $it ==> ${Thread.currentThread().name}")
                    it > 4
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    println("Collected 1: $it ==> ${Thread.currentThread().name}")
                }
        }catch (ex: Exception) {
            println("Exception; ${ex.message}")
        }
    }
}


fun checkMultipleFlow() {
    // Each collect gets all data, so each collect gets a separate flow.
    runBlocking {
        val pr = producer()
        launch {
            pr.collect {
                println("Collected @@@@@@: $it ==> ${Thread.currentThread().name}")
            }
        }
        delay(2500)
        pr.collect{
            println("Collected #####: $it ==> ${Thread.currentThread().name}")
        }
    }
}

fun checkSharedFlow() {
    // Each collect gets all data, so each collect gets a separate flow.
    runBlocking {
        val pr = sharedProducer()

        launch {
            pr.collect {
                println("Collected @@@@@@: $it ==> ${Thread.currentThread().name}")
            }
        }
        delay(2500)
        // Compilation error as it is readonly, need to return MutableSharedFlow
        // pr.emit()
        pr.collect{
            println("Collected #####: $it ==> ${Thread.currentThread().name}")
        }
    }
}


fun checkStateFlow() {
    // Each collect gets all data, so each collect gets a separate flow.
    runBlocking {
        val pr = stateProducer()
        launch {
            repeat(10) {
                delay(1000L)
                println("Collected %%%%%: ${pr.value} ==> ${Thread.currentThread().name}")

            }
        }

        launch {
            pr.collect {
                println("Collected @@@@@@: $it ==> ${Thread.currentThread().name}")
            }
        }
        delay(2500)
        // Compilation error as it is readonly, need to return MutableSharedFlow
        // pr.emit()
        pr.collect{
            println("Collected #####: $it ==> ${Thread.currentThread().name}")
        }
    }
}

fun producer(): Flow<Int> {
    return flow {
        repeat(10) {
            delay(500)
            println("Emitter $it ==> ${Thread.currentThread().name}")
            emit(it)
        }
        throw Exception("Complete exception")
    }.catch {
        println("Exception: ${it.message}")
        emit(10)
    }
}

fun sharedProducer(): Flow<Int> {
    val sf = MutableSharedFlow<Int>(replay = 2)
    GlobalScope.launch {
        repeat(10){
            delay(500L)
            sf.emit(it)
        }
    }
    return sf
}

fun stateProducer(): StateFlow<Int> {
    val st = MutableStateFlow(0)
    GlobalScope.launch {
        repeat(10){
            delay(500L)
            st.emit(it)
        }
    }
    return st
}

fun main() {
    // checkSharedFLow()
    // checkStateFLow()
    // checkFlow()
    // checkMultipleFlow()
    // checkSharedFlow()
    checkStateFlow()
}