package com.example.kotlin01

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
//coldFlow()
//cancelFlow()
//flowOperators()
    terminalFlowOperator()
}

fun terminalFlowOperator() {
    runBlocking {
        newTopic("Operadores Flow Terminales")
        newTopic("List")
        val numbersList= getDataByFlow()
            //.toList()
        println("List: $numbersList")

        newTopic("Single")
        val singleElement= getDataByFlow()
            .take(1)
            //.single()
        println("Single: $singleElement")

        newTopic("First")
        val firtElement= getDataByFlow()
            //.first()
        println("First element: $firtElement")

        newTopic("Last")
        //val elements= getDataByFlow().toList();
        //val lastElement=elements.last()
        //println("Last element: $lastElement")

        newTopic("Reduce")
        val totalCostInvoice = getDataByFlow()
            .reduce{accumulatedCost, nextCost->
                accumulatedCost+nextCost
            }

        println("Total cost invoice: $totalCostInvoice")

        newTopic("Fold")
        val generalTotalCostInvoice= getDataByFlow()
            .fold(totalCostInvoice) { accumulatedCost, nextCost ->
                accumulatedCost + nextCost
            }
        println("General total cost invoice: $generalTotalCostInvoice")


    }
}

fun flowOperators() {
    runBlocking {
        newTopic("Operadores Flow Intermediarios")

        newTopic("Map")

        getDataByFlow()
            .map {
                setFormatToCelsius(it)
                setFormatToCelsius(convertCelsiusToFahrenheit(it), "F")
            }
        //.collect { println(it) }

        //---------------------
        newTopic("Filter")
        getDataByFlow()
            .filter {
                it < 23
            }
            .map {
                setFormatToCelsius(it)
            }
           // .collect { println(it) }

        //---------------------
        newTopic("Transform")
        getDataByFlow()
            .transform {
                emit(setFormatToCelsius(it))
                emit(setFormatToCelsius(convertCelsiusToFahrenheit(it), "F"))
            }
            //.collect { println(it) }

        //---------------------
        newTopic("Take")
        getDataByFlow()
            .take(3)
            .map { setFormatToCelsius(it) }
            .collect { println(it) }

    }
}

fun convertCelsiusToFahrenheit(celsius: Float): Float = ((celsius * 9) / 5) + 32

fun setFormatToCelsius(temp: Float, degree: String = "C"): String {
//    return String.format(Locale.getDefault(), "%.1f°$degree", temp)
    return String.format("%.1f°$degree", temp)
}

fun cancelFlow() {
    runBlocking {
        newTopic("Cancelar Flow")
        val job = launch {
            getDataByFlow().collect { println(it) }
        }
        delay(someTime() * 2)
        job.cancel()
    }
}

fun coldFlow() {
//Los Flows Cold solo se empiezan a emitir despues de llamar a un operador terminal 'Collect'
    newTopic("Flows are Cold")
    runBlocking {
        val dataFlow = getDataByFlow()
        println("esperando...")
        delay(someTime())
        dataFlow.collect { println(it) }
    }
}

