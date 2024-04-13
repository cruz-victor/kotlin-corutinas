package com.example.kotlin01

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import java.lang.ArithmeticException
import java.util.concurrent.TimeoutException

fun main() {
//basicChannel()
//formasCerrarChannel()
//produceChannel()
//produceAndConsumeChannel()
//pipelines()
//pipelineWithStages()
//bufferChannel()
//excepcionesLaunchChannel()
//excepcionesAsycChannelv1()
    excepcionesAsycChannelv2()
    readLine()
//    formasEnviarElementosChannel()
//    formasRecivirElementosChannel()


}


fun pipelineWithStages() {
    runBlocking {
        println("1")
        val numbersChannel = produceIntegerNumbers() //Etapa 1: Producir numeros enteros
        println("2")
        val squaredChannel =
            produceSquareIntegerNumbers(numbersChannel) //Etapa 2: Calcular el cuadrado de cada numero de la etapa anterior
        println("3")
        val sumChannel =
            produceSumIntegerNumbers(squaredChannel) //Etapa 3: Acumula la suma de todos los numeros de la etapa anterior
        println("4")
        for (result in sumChannel) {
            println("--Resultado final: $result")
        }
        println("5")
        coroutineContext.cancelChildren() //Cancelar todas las coroutines
        println("6")
    }
}


//Etapa 1: Genera numeros enteros del 1 al 5 y envialos a un canal
//[1,2,3]
fun CoroutineScope.produceIntegerNumbers(): ReceiveChannel<Int> {
    return produce {
        for (i in 1..3) {
            delay(100)
            println("Etapa 1 - Numero entero producido: $i")
            send(i)
        }
    }
}

//Etapa 2: Recibe numero del canla, calcula el cuadrado y envia los resultado a otro canal
//[1,4,9]
fun CoroutineScope.produceSquareIntegerNumbers(numbersChannel: ReceiveChannel<Int>): ReceiveChannel<Int> {
    return produce {
        for (number in numbersChannel) {
            delay(500)
            println("Etapa 2 - Numero al cuadro producido: " + (number * number))
            send(number * number)
        }
    }
}

//Etapa 3: Recibe numeros del canal, acumula la suma y envia el resultado al final
//[1,4,9]=15
fun CoroutineScope.produceSumIntegerNumbers(squaredChannel: ReceiveChannel<Int>): ReceiveChannel<Int> {
    return produce {
        var sum = 0
        for (number in squaredChannel) {
            delay(800)
            sum += number
            println("Etapa 3 - Numero sumado: $sum")
            send(sum)
        }
    }
}

fun produceAndConsumeChannel() {
    runBlocking {
        val channel = Channel<Int>()

        val producerJob = launch {
            produceNumbers(channel, 10)
        }

        val consumerJos = launch {
            consumeNumbers(channel)
        }

        producerJob.join()
        consumerJos.join()
    }
}

fun produceNumbers(channel: SendChannel<Int>, maxValue: Int) {
    runBlocking {
        for (i in 1..maxValue) {
            println("Numero producido: $i")
            channel.send(i)
        }
        channel.close()
    }
}

fun consumeNumbers(channel: ReceiveChannel<Int>) {
    runBlocking {
        for (number in channel) {
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

fun excepcionesAsycChannelv1() {
    val exceptionHandler= CoroutineExceptionHandler{coroutineContext, throwable->
        println("Notifica al programador...$throwable en $coroutineContext")
    }

    CoroutineScope(Job()+exceptionHandler).launch {
        val result=async {
            delay(500)
            multiLambda(2,3){
                if (it>5) throw ArithmeticException()
            }
        }
        println("Result: ${result.await()}")
    }

    val channel=Channel<String>()

    CoroutineScope(Job()).launch(exceptionHandler) {
        delay(800)
        countries.forEach {
            channel.send(it)
            if (it.equals("Cochabamba")) channel.close()
        }
    }

    CoroutineScope(Job()).launch {
        channel.consumeEach { println(it) }
    }
}

fun excepcionesAsycChannelv2() {
    val exceptionGlobalHandler= CoroutineExceptionHandler{ coroutineContext, throwable->
        println("Notifica al programador...$throwable en $coroutineContext")
    }

    val coroutineScopeUno= CoroutineScope(Job()+CoroutineName("Coroutine Uno")+exceptionGlobalHandler)

    coroutineScopeUno.launch {
        val result=async {
            println("result delay...")
            delay(500)
            multiLambda(2,3){
                if (it>5) throw ArithmeticException()
            }
        }
        println("Result: ${result.await()}")
    }

    val channel=Channel<String>()

    println("antes del lauch countries...")
    val coroutineScopeDos= CoroutineScope(Job()+CoroutineName("Coroutine Dos")+exceptionGlobalHandler)
    coroutineScopeDos.launch {
        delay(800)
        countries.forEach {
            println("countries send...")
            channel.send(it)
            if (it.equals("Cochabamba")) channel.close()
        }
    }

    println("antes del lauch consume..")
    val coroutineScopeTres= CoroutineScope(Job()+CoroutineName("Coroutine Tres")+exceptionGlobalHandler)
    coroutineScopeTres.launch {
        channel.consumeEach {
            println("consumiend...$it")
            println(it)
        }
    }
}


fun excepcionesLaunchChannel() {
    //Manejador global de excepciones
    val exceptionHandler= CoroutineExceptionHandler{coroutineContext, throwable->
        println("Notifica al programador...$throwable en $coroutineContext")
    }

    runBlocking {
        newTopic("Manejo de excepciones clasico")
        launch {
            try {
                delay(2000)
                throw Exception()
            } catch (e: Exception) {
                println("Ocurrio una excepcion...")
            }
        }

        newTopic("Manejo de excepciones clasico v2")
        val coroutineScope= CoroutineScope(Job()+ CoroutineName("mi coroutine clasica") + exceptionHandler)
        coroutineScope.launch {
                delay(5000)
                throw Exception()
        }

        newTopic("Manejo de excepciones globales")
        val scope= CoroutineScope(Job() + CoroutineName("miCoroutine") + exceptionHandler)
        scope.launch() {
            delay(3000)
            throw TimeoutException()
        }
    }
}

fun bufferChannel() {
    runBlocking {
        newTopic("Channel sin Buffer")
        val time = System.currentTimeMillis()
        val channel = Channel<String>()
        launch {
            countries.forEach {
                delay(100)
                channel.send(it)
            }
            channel.close()
        }

        launch {
            delay(1_000)
            channel.consumeEach { println(it) }
            println("Time: ${System.currentTimeMillis() - time}ms")
        }

        newTopic("Channel con Buffer")
        val bufferTime = System.currentTimeMillis()
        val bufferChannel = Channel<String>(3)
        launch {
            countries.forEach {
                delay(100)
                bufferChannel.send(it)
            }
            bufferChannel.close()
        }

        launch {
            delay(1_000)
            bufferChannel.consumeEach { println(it) }
            println("Buffer Time: ${System.currentTimeMillis() - bufferTime}ms")
        }


    }
}

fun pipelines() {
    runBlocking {
        newTopic("pipelines")
        val citiesChannel = produceCities() //Etapa produccion de ciudades
        val foodsChannel = produceFoods(citiesChannel) //Etapa produccion de comidas por ciudad
        foodsChannel.consumeEach { println(it) }
        citiesChannel.cancel()
        foodsChannel.cancel() //cierra el channel
        println("Todo esta 10/10")
    }
}

fun CoroutineScope.produceFoods(cities: ReceiveChannel<String>): ReceiveChannel<String> {
    return produce {
        for (city in cities) {
            val food = getFoodByCity(city)
            send("$food desde $city")
        }
    }

}

suspend fun getFoodByCity(city: String): String {
    delay(300)
    return when (city) {
        "La Paz" -> "Plato paceño"
        "Cochabamba" -> "Chicharron"
        "Santa Cruz" -> "Majadito"
        else -> "Sin datos"
    }

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
