package com.example.kotlin01

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

fun main(){
//    dispatchers()
//    nested()
//    changeWithContext()
    //sequences()
    basicFlows()
}

fun basicFlows() {
    newTopic("Flows basic")
    runBlocking {
        startMessage("1 runBlocking")

        launch {
            startMessage("2 DataByFlow")
            getDataByFlow().collect { println(it) }
            endMessage("2 DataByFlow")
        }

        launch {
            startMessage("3 Other task")
            (1..50).forEach {
                delay(someTime()/10)
                println("Tarea 2")
            }
            endMessage("3 Other task")
        }

        endMessage("1 runBlocking")
    }
}

fun getDataByFlowWithDelayStatic(): Flow<Float> {
    //Los datos en el flow se van generando progresivamente. Similar a Sequence pero Asincrono (similar a Stream() de java)
    return flow {
        (1..5).forEach {
            println("procesando datos...")
            delay(300) //1500ms
            var randomNumber=20+it+ Random.nextFloat()
            println(randomNumber)
            emit(randomNumber)
        }
    }
}

fun getDataByFlow(): Flow<Float> {
    //Los datos en el flow se van generando progresivamente. Similar a Sequence pero Asincrono (similar a Stream() de java)
    return flow {
        (1..5).forEach {
            //println("procesando datos...")
            delay(someTime())
            var randomNumber=20+it+ Random.nextFloat()
            println("-Temperature generated: $randomNumber")
            emit(randomNumber)
        }
    }
}

fun getDataIntByFlow():Flow<Int>{
    return flow{
        (1..5).forEach {
            delay(someTime())
            println("-Number generated: $it")
            emit(it)

            if (it==3) throw Exception("Se alcanzo al numero de intentos igual a 3")
        }
    }
}



fun sequences() {
    newTopic("Sequences")
    getDataBySequence().forEach { println("${it}") }
}

fun getDataBySequence(): Sequence<Float> {
    //Los datos en las sequences se van generando progresivamente (similar a Stream() de java)
    return sequence {
        (1..5).forEach {
            println("procesando datos...")
            Thread.sleep(someTime())
            yield(20+it+ Random.nextFloat())
        }
    }
}

fun changeWithContext() {
    //Las siguientes lineas se ejecutan secuencialmente, withConext no crear procesos asincronos
    runBlocking {
        newTopic("withContext")
        startMessage("withContext main")

        withContext(newSingleThreadContext("newSingleThread 1")){
            startMessage("withConext newSingleThread")
            delay(someTime())
            println("contexto newSingleThread...")
            endMessage("withConext newSingleThread")
        }

        withContext(Dispatchers.IO){
            startMessage("withConext Dispatcher.IO")
            delay(someTime())
            println("contexto Dispatcher.IO...")
            endMessage("withConext Dispatcher.IO")
        }

        endMessage("withContext main")
    }
}

fun nested() {
    runBlocking {
        newTopic("Nested")

        val jobCoroutine1=launch {
            startMessage("Coroutine 1")

            launch {
                startMessage("Coroutine 1.1")
                delay(someTime())
                println("Coroutine 1.1...")
                endMessage("Coroutine 1.1")
            }

            launch (Dispatchers.IO) {
                startMessage("Coroutine 1.2")

                launch (newSingleThreadContext("newSingleTread 1.2.1")) {
                    startMessage("Coroutine 1.2.1")
                    //delay(someTime())
                    println("Coroutine 1.2.1...")
                    endMessage("Coroutine 1.2.1")
                }

                delay(someTime())
                println("after sometime 1.2")
                endMessage("Corotine 1.2")
            }

            var sum=0
            (1..100).forEach {
                sum+=it
                delay(someTime()/100)
            }
            println("Sum = $sum")
            endMessage("Coroutine 1")
        }

        delay(someTime()/2)
        jobCoroutine1.cancel()
        println("JobCoroutine2 cancelado...")
    }
}

fun dispatchers() {
    runBlocking {
        newTopic("Dispatchers")

        //Thread Main
        launch {
            startMessage("Main 1")
            println("Main 1...")
            endMessage("Main 1")
        }

        //Thread Default
        launch(Dispatchers.IO) {
            startMessage("IO")
            println("IO...")
            endMessage("IO")
        }

        //Thread Main
        launch(Dispatchers.Unconfined) {
            startMessage("Unconfined")
            println("Unconfined...")
            endMessage("Unconfined")
        }

        //Thread Main
        launch (){
            startMessage("Main 2")
            println("Main 2...")
            endMessage("Main 2")
        }

        //Thread Default
        launch(Dispatchers.Default) {
            startMessage("Default")
            println("Default...")
            endMessage("Default")
        }

        launch (newSingleThreadContext("nombre del hilo personalizado")) {
            startMessage("newSingleThreadContext")
            println("newSingleThreadContext...")
            endMessage("newSingleThreadContext")
        }

        //Dispatchers.Main solo funciona en Android
//        launch (Dispatchers.Main){
//            startMessage("Main 2")
//            println("Main 2...")
//            endMessage("Main 2")
//        }

    }
}
