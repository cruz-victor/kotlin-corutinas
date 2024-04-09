package com.example.kotlin01

fun main(){
    print("Hellow vic")
    channelsBasic()
    formasEnviarElementosChannel()
    formasRecivirElementosChannel()
    formasCerrarChannel()
    pipelines()
    bufferChannel()
    manejoExcepcionesChannel()

}

fun manejoExcepcionesChannel() {
    newTopic("manejoExcepcionesChannel")
}

fun bufferChannel() {
    newTopic("bufferChannel")
}

fun pipelines() {
    newTopic("pipelines")
}

fun formasCerrarChannel() {
    newTopic("formasCerrarChannel")
}

fun formasRecivirElementosChannel() {
    newTopic("formasRecivirElementosChannel")
}

fun formasEnviarElementosChannel() {
    newTopic("formasEnviarElementosChannel")
}

fun channelsBasic() {
    newTopic("channelsBasic")
}
