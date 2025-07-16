package com.example.recetasdecocina.model

import java.util.UUID

data class Recipe(
    val recipeId: String = UUID.randomUUID().toString(), // Usamos String para UUID
    var name: String,
    var description: String? = null, // Puede ser nulo
    var instructions: String,
    var prepTimeMinutes: Int,
    var cookTimeMinutes: Int,
    var servings: Int,
    var imageUrl: String? = null, // Puede ser nulo
    var isFavorite: Boolean = false, // Para el estado favorito en la UI
    val creationDate: Long = System.currentTimeMillis(), // Marca de tiempo de creación
    // val creatorUserId: String? = null // Si implementaras usuarios y un backend
    val ingredients: MutableList<IngredientItem> = mutableListOf() // Lista de ingredientes de esta receta
)