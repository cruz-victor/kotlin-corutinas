package com.example.kotlin01

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

fun main(){
    //globalScope()
    //suspendFunctions()
    newTopic("Constructore de coroutines")
    //contructorRunBlocking()
    //contructorLaunch()
    //constructorAsync()
    //constructorAsyncv2()
    contructorProduce()
    //newTopic("Job and Deferred")
    //job()
    //deferred()
    //deferredv2()

    readLine()
}

fun contructorProduce() {
    runBlocking {

        newTopic("Produce")
        val lastNames=produceLastNames()
        lastNames.consumeEach { println(it) }
    }
}

fun CoroutineScope.produceLastNames():ReceiveChannel<String> = produce{
            (1..5).forEach { send("lastName $it") }
}

fun deferredv2() {
    runBlocking {
        newTopic("Deferred")

        var deferred=async {
            multi(5,7)
        }.await()

        delay(3_000)
        println("Valor del deferred value: ${deferred} ")
    }
}

fun deferred() {
    runBlocking {
        newTopic("Deferred")

        var deferred=async {
            startMessage()
            delay(someTime())
            println("Asyc...")
            endMessage()
            multi(5,5)
        }

        delay(3_000)
        println("Deferred: ${deferred}")
        println("Valor del deferred.awaid: ${deferred.await()} ")
    }
}

fun job() {
    runBlocking {
        newTopic("Job")

        var job=launch {
            startMessage()
            delay(someTime())
            println("Job...")
            endMessage()
        }

        //delay(4_000)
        println("Job: $job")
        println("isActive: ${job.isActive} ")
        println("isCancelled: ${job.isCancelled} ")
        println("isCompleted: ${job.isCompleted} ")

        //delay(someTime())
        println("Tarea cancelda o interrupinda")
        job.cancel()

        println("isActive: ${job.isActive} ")
        println("isCancelled: ${job.isCancelled} ")
        println("isCompleted: ${job.isCompleted} ")
    }
}

fun constructorAsync() {
    runBlocking {
        newTopic("Async")

        var result=async {
            startMessage()
            delay(someTime())
            println("Async...")
            endMessage()
            1
        }
        print("Result: ${result.await()}")
    }
}

fun constructorAsyncv2() {
    runBlocking {
        newTopic("Async")

        var result=async {
            startMessage()
            delay(someTime())
            println("Async...")
            endMessage()
            1
        }.await()
        print("Result: ${result}")
    }
}

fun contructorLaunch() {
    runBlocking {
        newTopic("Launch")

        launch {
            startMessage()
            delay(someTime())
            println("Launch...")
            endMessage()
        }
    }
}

fun contructorRunBlocking() {
    newTopic("RunBlocking")

    runBlocking{
        startMessage()
        delay(someTime())
        println("RunBlocking...")
        endMessage()
    }
}

fun suspendFunctions() {
    newTopic("Suspend Function")
    Thread.sleep(someTime())

    GlobalScope.launch {
        println("Delay...")
        delay(someTime())
    }
}

fun globalScope() {
    newTopic("Global Scope")
    GlobalScope.launch {
        startMessage()
        delay(someTime())
        println("Mi corrutina")
        endMessage()
    }
}

fun startMessage() {
    println("--- Comenzando coroutine --- Hilo: ${Thread.currentThread().name}")
}

fun endMessage() {
    println("--- Coroutine finalizado --- Hilo: ${Thread.currentThread().name}")
}
