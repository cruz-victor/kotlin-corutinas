package com.example.kotlin01

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
//coldFlow()
//cancelFlow()
//flowOperators()
//terminalFlowOperator()
//bufferFlow()
//conflateFlow()
//multiFlow()
//flatFlow()
//flowExceptionsV1()
    flowExceptionsV2()

}

fun flowExceptionsV2() {
    runBlocking {
        newTopic("Control de errores")
        newTopic("Try/Catch")

        getDataIntByFlow()
            .catch {
                //Salta a esta instruccion cuando en el generador de flujo de numeros hay un numero 3 y se lanza una excepcion
                emit(-1)
            }
            .collect {
                println(it)
                if (it==-1) println("Notifica al programador...")
            }
    }
}

fun flowExceptionsV1() {
    runBlocking {
        newTopic("Control de errores")
        newTopic("Try/Catch")

        try {
            getDataIntByFlow()
                .collect {
                    println(it)
                    if (it==3) throw Exception("Se alcanzo al numero de intentos igual a 3")
                }
        }catch (e:Exception){
            println("Ocurrio una excepcion...")
            //e.printStackTrace()
        }

    }
}

fun flatFlow() {
    runBlocking {
        newTopic("Aplanamiento de Flujos")

        newTopic("FlatMapConcat")

        getCitiesFlow()
            .flatMapConcat{ city->
                getLastThreeTemperaturesByCityFlow(city)
            }
            .map { setFormatToCelsius(it) }
            .collect { println(it) }


        newTopic("FlatMapMerge")

        getCitiesFlow()
            .flatMapMerge{ city->
                getLastThreeTemperaturesByCityFlow(city)
            }
            .map { setFormatToCelsius(it) }
            //.collect { println(it) }
    }
}

fun getCitiesFlow():Flow<String>{
    println("Servicio web - Consultando ciudades ...")
    return flow{
        listOf("La Paz","Cochabamba","Santa Cruz")
            .forEach { city->
                delay(2_000)
                println("- Ciudad consultada: $city")
                emit(city)
            }
    }
}



fun getLastThreeTemperaturesByCityFlow(city:String):Flow<Float>{
    println("Servicio web - Consultando 3 temperaturas para $city ...")
    return flow {
        (1..3).forEach {
            println("- Temperatura $it")
            delay(1_000)
            emit(Random.nextInt(10,30).toFloat())
        }
    }
}


fun multiFlow() {
    runBlocking {
        newTopic("MultiFlow")

        newTopic("Zip")
        getDataByFlow()
            .map {
                setFormatToCelsius(it)
            }
            .zip(getDataIntByFlow()) { degrees, result ->
                "key: $result - value: $degrees"
            }
            .collect { println(it) }

        newTopic("Combine")
        getDataByFlow()
            .map {
                setFormatToCelsius(it)
            }
            .combine(getDataIntByFlow()){ degrees, result->
                "key: $result - value $degrees"
            }
            //.collect { println(it) }
    }
}

fun conflateFlow() {
    //Conflate = Zona de almacenamiento temporal/Memoria intermedia
    //Usar cuando los datos producidos (flow) son mas rapidos que los datos consumidos (collect)
    //No mantiene todos los elementos del flow, los solapa
    runBlocking {
        newTopic("Conflate flow")
        val time = measureTimeMillis {
            getDataByFlowWithDelayStatic()
                .map { setFormatToCelsius(it) }
                .conflate() //Sin conflate=4635ms ; Con conflate=2831ms //Diferencia= 1804ms
                .collect {
                    delay(600)
                    println("collect print: $it")
                }
        }
        println("Time: $time")
    }
}

fun bufferFlow() {
    //Buffer = Zona de almacenamiento temporal/Memoria intermedia
    //Usar cuando los datos producidos (flow) son mas rapidos que los datos consumidos (collect)
    //Mantiene todos los elementos del flow
    runBlocking {
        newTopic("Buffer flow")
        val time = measureTimeMillis {
            getDataByFlowWithDelayStatic()
                .map { setFormatToCelsius(it) }
                .buffer() //Sin buffer=4642ms ; Con buffer=3431ms //Diferencia= 1211ms
                .collect {
                    delay(600) //2500ms
                    println("collect print: $it")
                }
        }

        println("Time: $time")

    }
}

fun terminalFlowOperator() {
    runBlocking {
        newTopic("Operadores Flow Terminales")
        newTopic("List")
        val numbersList = getDataByFlow()
        //.toList()
        println("List: $numbersList")

        newTopic("Single")
        val singleElement = getDataByFlow()
            .take(1)
        //.single()
        println("Single: $singleElement")

        newTopic("First")
        val firtElement = getDataByFlow()
        //.first()
        println("First element: $firtElement")

        newTopic("Last")
        //val elements= getDataByFlow().toList();
        //val lastElement=elements.last()
        //println("Last element: $lastElement")

        newTopic("Reduce")
        val totalCostInvoice = getDataByFlow()
            .reduce { accumulatedCost, nextCost ->
                accumulatedCost + nextCost
            }

        println("Total cost invoice: $totalCostInvoice")

        newTopic("Fold")
        val generalTotalCostInvoice = getDataByFlow()
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

