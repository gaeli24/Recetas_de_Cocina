package com.example.recetasdecocina.model

data class IngredientItem(
    val name: String,
    val quantity: String, // Usamos String para manejar "1/2 taza", "2 piezas"
    val unit: String? = null // Puede ser nulo (ej. para "sal al gusto")
)