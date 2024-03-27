package com.example.kotlin01

import kotlinx.coroutines.*

fun main(){
//    dispatchers()
//    nested()
    changeWithContext()
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
