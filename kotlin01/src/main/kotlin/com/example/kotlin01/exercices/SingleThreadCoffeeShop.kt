package com.example.kotlin01.exercices

import com.example.kotlin01.newTopic
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.log

fun main(){
    newTopic("Single thread coffee shop")
//    Pasos para preparar caffe capuchino
//    1. Tomar la orden de cafe
//    2. Moler los grandos de cafe (30 segundos)
//    3. Tomar un trago de caffe espresso (20 segundos)
//    4. Evaporar la leche (10 segundos)
//    5. Mezclar la leche y el caffe esprsso (5 segundos)
//    6. Servir el caffe capuchino
    runBlocking {
        launch (CoroutineName("barista 1")) { makeCapuchinoCoffee() }
        launch (CoroutineName("barista 2")) { makeCapuchinoCoffee() }
        launch (CoroutineName("barista 3")) { makeCapuchinoCoffee() }
    }
}

suspend
fun makeCapuchinoCoffee() {
    val coffeBeans= "Colombian coffee beans"
    val milk="Fresh milk"

    println("Orden: Caffe cappuccino - $3.5")

    val groundBeans= grindCoffeBeans(coffeBeans)
    val espressoShot=pullEspressoShot(groundBeans)
    val steamedMilk=steamMilk(milk)
    val cappucino = makeCappuccino(espressoShot, steamedMilk)
    
    println("Servir: $cappucino")

}

suspend fun makeCappuccino(espressoShot: Any, steamedMilk: Any): Any {
//    runBlocking {
        println("Mezclando la leche con el cafe...")
        delay(5000)
//    }
    return "Caffe cappuccino"
}

suspend fun  steamMilk(milk: String): String {
//    runBlocking {
        println("Evaporando la leche...")
        delay(10000)
//    }
    return "Leche evaporada"
}

suspend fun pullEspressoShot(groundBeans: String): String {
//    runBlocking {
        println("Obteniendo un trago de caffe espresso...")
        delay(20000)
//    }
    return "Caffe espresso"
}

suspend fun grindCoffeBeans(coffeBeans: String): String {
//    runBlocking {
        println("Moliendo los granos de cafe...")
        delay(30000)
//    }
    return "Caffe molido"
}


