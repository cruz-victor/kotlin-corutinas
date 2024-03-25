package com.example.kotlin01

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
    runBlocking {
        val channelNumbers= Channel<Int>()
        ProducerNumbers(channelNumbers)
        ConsumerNumbers(channelNumbers)
    }
}

private fun CoroutineScope.ProducerNumbers(channelNumbers: Channel<Int>) {
    launch {
        for (i in 1..5) {
            delay(1000)
            println("Productor produce: ${i}")
            channelNumbers.send(i)
        }
        channelNumbers.close()
    }
}

private fun CoroutineScope.ConsumerNumbers(channelNumbers: Channel<Int>) {
    launch {
        for (element in channelNumbers) {
            println("Consumidor consume: ${element}")
        }
    }
}