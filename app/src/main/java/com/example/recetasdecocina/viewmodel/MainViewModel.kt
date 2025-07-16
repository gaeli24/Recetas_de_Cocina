package com.example.recetasdecocina.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recetasdecocina.model.Recipe
import com.example.recetasdecocina.repository.RecipeRepository

// MainViewModel se encarga de la lógica para la pantalla principal de la lista de recetas
class MainViewModel(private val repository: RecipeRepository) : ViewModel() {

    // Exponemos el LiveData de la lista de recetas desde el repositorio
    // La UI (MainActivity) observará este LiveData para actualizarse
    val recipes: LiveData<MutableList<Recipe>> = repository.recipes // CAMBIA ESTA LÍNEA

    // Método para añadir una nueva receta
    fun addRecipe(recipe: Recipe) {
        repository.addRecipe(recipe)
    }

    // Método para actualizar una receta existente
    fun updateRecipe(updatedRecipe: Recipe) {
        repository.updateRecipe(updatedRecipe)
    }

    // Método para eliminar una receta
    fun deleteRecipe(recipeId: String) {
        repository.deleteRecipe(recipeId)
    }

    // Método para buscar una receta por ID
    fun getRecipeById(recipeId: String): Recipe? {
        return repository.getRecipeById(recipeId)
    }

    // Método para alternar el estado de favorito de una receta
    fun toggleFavoriteStatus(recipeId: String) {
        repository.toggleFavoriteStatus(recipeId)
    }
}

// --- Clase Factory para instanciar el ViewModel con dependencias ---
// Esta clase es necesaria para que ViewModel sepa cómo crear una instancia de MainViewModel
// que requiere un RecipeRepository como argumento.
class MainViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}