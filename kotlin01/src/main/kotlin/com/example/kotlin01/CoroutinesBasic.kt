package com.example.kotlin01

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main(){
    globalScope()
    readLine()
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
