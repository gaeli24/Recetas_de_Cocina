package com.example.recetasdecocina.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // Necesitarás añadir la dependencia de Glide
import com.example.recetasdecocina.databinding.ItemRecipeCardBinding // Generado por View Binding
import com.example.recetasdecocina.model.Recipe

// Define una interfaz para los clics en los elementos
interface OnRecipeClickListener {
    fun onRecipeClick(recipe: Recipe)
    fun onFavoriteClick(recipe: Recipe)
}

class RecipeAdapter(private val listener: OnRecipeClickListener) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipes = mutableListOf<Recipe>()

    // Método para actualizar los datos del adaptador
    fun submitList(newRecipes: MutableList<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged() // Notifica al RecyclerView que los datos han cambiado
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        // Infla el layout de la tarjeta de receta usando View Binding
        val binding = ItemRecipeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe, listener)
    }

    override fun getItemCount(): Int = recipes.size

    // ViewHolder anidado para contener las vistas de cada elemento de la lista
    class RecipeViewHolder(private val binding: ItemRecipeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe, listener: OnRecipeClickListener) {
            binding.apply {
                textViewRecipeName.text = recipe.name
                textViewPrepTime.text = "${recipe.prepTimeMinutes} min" // Combina tiempo de preparación y cocción si lo deseas

                // Carga la imagen de la receta usando Glide
                // Asegúrate de que el URL de la imagen no sea nulo antes de cargarlo
                recipe.imageUrl?.let { url ->
                    Glide.with(imageViewRecipe.context)
                        .load(url)
                        .placeholder(com.google.android.material.R.drawable.design_ic_visibility_off) // Un placeholder temporal si la imagen no carga
                        .error(com.google.android.material.R.drawable.design_ic_visibility_off) // Una imagen de error
                        .centerCrop()
                        .into(imageViewRecipe)
                } ?: run {
                    // Si no hay URL, puedes ocultar el ImageView o mostrar una imagen por defecto
                    imageViewRecipe.setImageResource(com.google.android.material.R.drawable.design_ic_visibility_off) // Reemplaza con un drawable por defecto
                }


                // Configura el icono de favorito según el estado de la receta
                if (recipe.isFavorite) {
                    imageViewFavorite.setImageResource(com.example.recetasdecocina.R.drawable.ic_favorite_filled)
                } else {
                    imageViewFavorite.setImageResource(com.example.recetasdecocina.R.drawable.ic_favorite_border)
                }

                // Manejadores de clic
                // Clic en toda la tarjeta (para ir a los detalles de la receta, por ejemplo)
                root.setOnClickListener {
                    listener.onRecipeClick(recipe)
                }

                // Clic en el icono de favorito
                imageViewFavorite.setOnClickListener {
                    listener.onFavoriteClick(recipe)
                }
                // Dentro de RecipeAdapter.kt, en la función bind del RecipeViewHolder:
// ...
                recipe.imageUrl?.let { url ->
                    Glide.with(imageViewRecipe.context)
                        .load(url)
                        .placeholder(com.google.android.material.R.drawable.design_ic_visibility_off) // Imagen temporal mientras carga
                        .error(com.google.android.material.R.drawable.design_ic_visibility_off)     // Imagen si hay un error al cargar
                        .centerCrop()
                        .into(imageViewRecipe)
                } ?: run {
                    // Si no hay URL, se usa una imagen por defecto local
                    imageViewRecipe.setImageResource(com.google.android.material.R.drawable.design_ic_visibility_off) // Puedes cambiarla por una tuya
                }
// ...

                // Actualiza la contentDescription de la imagen de la receta
                imageViewRecipe.contentDescription = "Imagen de la receta: ${recipe.name}"
            }
        }
    }
}