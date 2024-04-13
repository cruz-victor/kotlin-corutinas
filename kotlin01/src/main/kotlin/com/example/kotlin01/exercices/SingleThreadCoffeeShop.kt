package com.example.kotlin01.exercices

import com.example.kotlin01.newTopic
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

//    Pasos para preparar caffe capuchino
//    1. Tomar la orden de cafe
//    2. Moler los grandos de cafe (30 segundos)
//    3. Tomar un trago de caffe espresso (20 segundos)
//    4. Evaporar la leche (10 segundos)
//    5. Mezclar la leche y el caffe esprsso (5 segundos)
//    6. Servir el caffe capuchino

fun main() {
    //execution time: 130082 ms
    newTopic("Single thread coffee shop")

    val time = measureTimeMillis {
        makeCapuchinoCoffee()
        makeCapuchinoCoffee()
    }

    println("Execution time: $time ms")
}

fun makeCapuchinoCoffee() {
    val coffeBeans = "Colombian coffee beans"
    val milk = "Fresh milk"

    println("${printCoroutineInfo2()} Orden: Caffe cappuccino - $3.5")

    val groundBeans = grindCoffeBeans(coffeBeans)
    val espressoShot = pullEspressoShot(groundBeans)
    val steamedMilk = steamMilk(milk)
    val cappucino = makeCappuccino(espressoShot, steamedMilk)

    println("${printCoroutineInfo2()} Servir: $cappucino")
}

fun makeCappuccino(espressoShot: Any, steamedMilk: Any): Any {
    println("${printCoroutineInfo2()} Mezclando la leche con el cafe...5s")
    Thread.sleep(5000)

    return "Caffe cappuccino"
}

fun steamMilk(milk: String): String {
    println("${printCoroutineInfo2()} Evaporando la leche...10s")
    Thread.sleep(10000)

    return "Leche evaporada"
}

fun pullEspressoShot(groundBeans: String): String {
    println("${printCoroutineInfo2()} Obteniendo un trago de caffe espresso...20s")
    Thread.sleep(20000)

    return "Caffe espresso"
}

fun grindCoffeBeans(coffeBeans: String): String {
    println("${printCoroutineInfo2()} Moliendo los granos de cafe...30s")
    Thread.sleep(30000)

    return "Caffe molido"
}

suspend fun printCoroutineInfo(): Any {
    val coroutineName = coroutineContext[CoroutineName]?.name ?: "UnknownCoroutine"
    val jobInfo = coroutineContext.job.toString()
    val threadName = Thread.currentThread().name

    return ("[$threadName-$coroutineName-$jobInfo]")
}

fun printCoroutineInfo2(): Any {
    val threadName = Thread.currentThread().name

    return ("[$threadName]")
}