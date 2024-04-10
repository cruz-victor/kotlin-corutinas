package com.example.kotlin01

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
//basicChannel()
//formasCerrarChannel()
produceChannel()
// produceAndConsumeChannel()
//    formasEnviarElementosChannel()
//    formasRecivirElementosChannel()

//    pipelines()
//    bufferChannel()
//    manejoExcepcionesChannel()

}

fun produceAndConsumeChannel() {
    runBlocking {
        val channel= Channel<Int>()

        val producerJob=launch{
            produceNumbers(channel, 10)
        }

        val consumerJos=launch {
            consumeNumbers(channel)
        }

        producerJob.join()
        consumerJos.join()
    }
}

fun produceNumbers(channel:SendChannel<Int>, maxValue:Int){
    runBlocking {
        for (i in 1..maxValue){
            println("Numero producido: $i")
            channel.send(i)
        }
        channel.close()
    }
}

fun consumeNumbers(channel: ReceiveChannel<Int>){
    runBlocking {
        for (number in channel){
            println("Numero recibido: $number")
            delay(100)
        }
        println("Consumidor finalizado")
    }
}

fun produceChannel() {
    runBlocking {
        newTopic("Canales y el patron productor-consumidor")
        val names = produceCities()
        names.consumeEach { println(it) }
    }
}

fun CoroutineScope.produceCities(): ReceiveChannel<String> {
    return produce {
        countries.forEach {
            send(it)
        }
    }
}

fun manejoExcepcionesChannel() {
//    newTopic("manejoExcepcionesChannel")
    println("manejoExcepcionesChannel")
}

fun bufferChannel() {
//    newTopic("bufferChannel")
    println("bufferChannel")
}

fun pipelines() {
//    newTopic("pipelines")
    println("pipelines")
}

fun formasCerrarChannel() {
    runBlocking {
        newTopic("Cerrar un canal")
        val channel = Channel<String>()

        launch {
            countries.forEach {
                channel.send(it)
                //if (it.equals("Cochabamba")) channel.close() //Lanza una exception porque el channel esta cerrado al intentar enviar

                if (it.equals("Cochabamba")) {
                    channel.close()
                    return@launch
                }
            }
            //channel.close()
        }

        //v1 consumidor de channel
//        for (value in channel){
//            println(value)
//        }

        //v2 consumidor de channel
//        try {
//            while(!channel.isClosedForReceive){
//                println(channel.receive())
//            }
//        }catch (e: ClosedReceiveChannelException){
//            println("El canal esta cerrado para recibir")
//        }

        //v3 consumidor de channel
        channel.consumeEach { println(it) }

    }
}

fun formasRecivirElementosChannel() {
//    newTopic("formasRecivirElementosChannel")
    println("formasRecivirElementosChannel")
}

fun formasEnviarElementosChannel() {
//    newTopic("formasEnviarElementosChannel")
    println("formasEnviarElementosChannel")
}

val countries = listOf("La Paz", "Cochabamba", "Santa Cruz")

fun basicChannel() {
    runBlocking {
        newTopic("channelsBasic")
        val channel = Channel<String>()

        launch {
            countries.forEach {
                channel.send(it)
            }
        }

//        repeat(3){
//            println(channel.receive())
//        }

        for (value in channel) {
            //println(channel.receive()) //No funciona porque los channel estan disenados para solucionar concurrencia entre coroutines
            println(value)
        }
    }
}
