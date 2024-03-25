package com.example.kotlin01

import kotlinx.coroutines.*

fun main(){
    //globalScope()
    //suspendFunctions()
    newTopic("Constructore de coroutines")
    //contructorRunBlocking()
    //contructorLaunch()
    //constructorAsync()
    constructorAsyncv2()
    readLine()
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
