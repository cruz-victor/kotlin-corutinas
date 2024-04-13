package com.example.kotlin01.exercices

import com.example.kotlin01.newTopic
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main(){
    newTopic("Channels **")
    runBlocking {
        newTopic("Single thread coffee shop")
//    Pasos para preparar caffe capuchino
//    1. Tomar la orden de cafe
//    2. Moler los grandos de cafe (30 segundos)
//    3. Tomar un trago de caffe espresso (20 segundos)
//    4. Evaporar la leche (10 segundos)
//    5. Mezclar la leche y el caffe esprsso (5 segundos)
//    6. Servir el caffe capuchino

        val orders = listOf(
            Menu.Cappuccino(CoffeeBean.Regular, Milk.Whole),
            Menu.Cappuccino(CoffeeBean.Premium, Milk.Breve),
            Menu.Cappuccino(CoffeeBean.Regular, Milk.NonFat),
            Menu.Cappuccino(CoffeeBean.Decaf, Milk.Whole),
            Menu.Cappuccino(CoffeeBean.Regular, Milk.NonFat),
            Menu.Cappuccino(CoffeeBean.Decaf, Milk.NonFat),
        )

        val ordersChannel = Channel<Menu>()

        launch {
            for (order in orders){
                ordersChannel.send(order)
            }
            ordersChannel.close()
        }

        val time= measureTimeMillis {
            coroutineScope {
                launch(CoroutineName("barista 1")) { makeCapuchinoCoffee() }
                launch(CoroutineName("barista 2")) { makeCapuchinoCoffee() }
                launch(CoroutineName("barista 3")) { makeCapuchinoCoffee() }
            }
        }

        println("Execution time: $time ms")
    }
}