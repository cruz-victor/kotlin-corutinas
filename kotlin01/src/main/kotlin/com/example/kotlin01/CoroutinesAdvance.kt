package com.example.kotlin01

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
//basicChannel()
//    formasEnviarElementosChannel()
//    formasRecivirElementosChannel()
//formasCerrarChannel()
//    pipelines()
//    bufferChannel()
//    manejoExcepcionesChannel()

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
        val channel=Channel<String>()

        launch {
            countries.forEach {
                channel.send(it)
                //if (it.equals("Cochabamba")) channel.close() //Lanza una exception porque el channel esta cerrado al intentar enviar

                if (it.equals("Cochabamba")){
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

val countries = listOf("La Paz", "Cochabamba","Santa Cruz")

fun basicChannel() {
    runBlocking {
        newTopic("channelsBasic")
        val channel= Channel<String>()

        launch {
            countries.forEach {
                channel.send(it)
            }
        }

//        repeat(3){
//            println(channel.receive())
//        }

        for(value in channel){
            //println(channel.receive()) //No funciona porque los channel estan disenados para solucionar concurrencia entre coroutines
            println(value)
        }
    }
}
