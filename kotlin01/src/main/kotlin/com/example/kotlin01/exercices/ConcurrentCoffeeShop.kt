package com.example.kotlin01.exercices

import com.example.kotlin01.newTopic
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

fun main() {
    //execution time: 65064 ms
    runBlocking {
        newTopic("Single thread coffee shop")
//    Pasos para preparar caffe capuchino
//    1. Tomar la orden de cafe
//    2. Moler los grandos de cafe (30 segundos)
//    3. Tomar un trago de caffe espresso (20 segundos)
//    4. Evaporar la leche (10 segundos)
//    5. Mezclar la leche y el caffe esprsso (5 segundos)
//    6. Servir el caffe capuchino
        val time= measureTimeMillis {
            coroutineScope {
                launch(CoroutineName("barista 1")) { makeCapuchinoCoffeeII() }
                launch(CoroutineName("barista 2")) { makeCapuchinoCoffeeII() }
            }
        }

        println("Execution time: $time ms")
    }
}

suspend fun makeCapuchinoCoffeeII() {
    val coffeBeans = "Colombian coffee beans"
    val milk = "Fresh milk"

    println("${printCoroutineInfoII()} Orden: Caffe cappuccino - $3.5")

    val groundBeans = grindCoffeBeansII(coffeBeans)
    val espressoShot = pullEspressoShotII(groundBeans)
    val steamedMilk = steamMilkII(milk)
    val cappucino = makeCappuccinoII(espressoShot, steamedMilk)

    println("${printCoroutineInfoII()} Servir: $cappucino")
}

suspend fun makeCappuccinoII(espressoShot: Any, steamedMilk: Any): Any {
    println("${printCoroutineInfoII()} Mezclando la leche con el cafe...5s")
    delay(5000)

    return "Caffe cappuccino"
}

suspend fun steamMilkII(milk: String): String {
    println("${printCoroutineInfoII()} Evaporando la leche...10s")
    delay(10000)

    return "Leche evaporada"
}

suspend fun pullEspressoShotII(groundBeans: String): String {
    println("${printCoroutineInfoII()} Obteniendo un trago de caffe espresso...20s")
    delay(20000)

    return "Caffe espresso"
}

suspend fun grindCoffeBeansII(coffeBeans: String): String {
    println("${printCoroutineInfoII()} Moliendo los granos de cafe...30s")
    delay(30000)

    return "Caffe molido"
}

suspend fun printCoroutineInfoII(): Any {
    val coroutineName = coroutineContext[CoroutineName]?.name ?: "UnknownCoroutine"
    val jobInfo = coroutineContext.job.toString()
    val threadName = Thread.currentThread().name

    return ("[$threadName-$coroutineName-$jobInfo]")
}