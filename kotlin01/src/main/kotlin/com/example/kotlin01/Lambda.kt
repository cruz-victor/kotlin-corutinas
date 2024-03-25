package com.example.kotlin01

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {
    //lambda()
    //thread()
    couritinesVsThread()
}

fun couritinesVsThread() {
    newTopic("Corrutinas vs Threads")
    //Creating one millon threads (It's not optimal)
    //Exception in thread "main" java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached
//    (1..1_000_000).forEach{
//        thread {
//            Thread.sleep(someTime())
//            print("*")
//        }
//    }

    runBlocking {
        (1..1_000_000).forEach {
            launch {
                delay(someTime())
                print("*")
            }
        }
    }

}

private const val SEPARATOR = "=================="
fun newTopic(topic: String) {
    println("\n $SEPARATOR $topic $SEPARATOR\n")
}

fun thread() {
    newTopic("Thread")
    println(multithread(5, 3))
    println(multithreadLambda(5, 4) { c -> println(c) })
}

fun multithread(a: Int, b: Int): Int {
    var c = 0
    thread {
        Thread.sleep(someTime())
        c = a * b
    }

    Thread.sleep(2_100)
    return c
}

fun multithreadLambda(a: Int, b: Int, callback: (c: Int) -> Unit) {
    var result = 0
    thread {
        Thread.sleep(someTime())
        result = a * b
        callback(result)
    }
}


fun someTime(): Long = Random.nextLong(500, 2_000)

fun lambda() {
    newTopic("Lambda")
    println(multi(5, 1))

    println(multiLambda(5, 2) { c ->
        println(c)
    })
}

fun multi(a: Int, b: Int): Int {
    return a * b
}

fun multiLambda(a: Int, b: Int, result: (c: Int) -> Unit) {
    result(a * b)
}