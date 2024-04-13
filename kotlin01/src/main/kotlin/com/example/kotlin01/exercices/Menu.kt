package com.example.kotlin01.exercices

class Menu(coffeeBean: CoffeeBean, milk: Milk) {

    companion object {
        // Método estático para preparar un Cappuccino con ingredientes específicos
        fun Cappuccino(coffeeBean: CoffeeBean, milk: Milk):Menu {
            println(coffeeBean)
            println(milk)
            return Menu(coffeeBean, milk)
        }
    }
}
