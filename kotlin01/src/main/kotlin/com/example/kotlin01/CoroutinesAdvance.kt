package com.example.kotlin01

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
    basicChannel()
//    formasEnviarElementosChannel()
//    formasRecivirElementosChannel()
//    formasCerrarChannel()
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
//    newTopic("formasCerrarChannel")
    println("formasCerrarChannel")
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
            println(channel.receive()) //No funciona porque los channel estan disenados para solucionar concurrencia entre coroutines
            //println(value)
        }
    }
}
