package com.example.recetasdecocina

import android.os.Bundle
import android.widget.Toast // Para mostrar mensajes temporales
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetasdecocina.adapter.OnRecipeClickListener
import com.example.recetasdecocina.adapter.RecipeAdapter
import com.example.recetasdecocina.databinding.ActivityMainBinding // Generado por View Binding
import com.example.recetasdecocina.model.Recipe
import com.example.recetasdecocina.repository.RecipeRepository
import com.example.recetasdecocina.viewmodel.MainViewModel
import com.example.recetasdecocina.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity(), OnRecipeClickListener {

    private lateinit var binding: ActivityMainBinding // Instancia de la clase de binding
    private lateinit var viewModel: MainViewModel
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Infla el layout usando View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Establece la vista raíz del binding como el contenido de la Activity

        // 1. Configurar la Toolbar
        setSupportActionBar(binding.toolbar) // Establece la Toolbar como la ActionBar de la actividad
        binding.toolbarTitle.text = getString(R.string.app_toolbar_title) // Asigna el título desde strings.xml

        // 2. Inicializar el ViewModel
        // Creamos una instancia de la factory, pasándole el repositorio Singleton
        val viewModelFactory = MainViewModelFactory(RecipeRepository)
        // Obtenemos la instancia del ViewModel usando la factory
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        // 3. Configurar el RecyclerView y el Adapter
        recipeAdapter = RecipeAdapter(this) // Pasamos 'this' porque MainActivity implementa OnRecipeClickListener
        binding.recyclerViewRecipes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity) // Usamos un LayoutManager lineal
            adapter = recipeAdapter // Asignamos el adaptador
        }

        // 4. Observar los cambios en la lista de recetas desde el ViewModel
        viewModel.recipes.observe(this) { recipes ->
            // Cada vez que la lista de recetas cambie en el ViewModel, actualizamos el adaptador
            recipeAdapter.submitList(recipes)
        }

        // 5. Configurar el FloatingActionButton (FAB)
        binding.fabAddRecipe.setOnClickListener {
            // Aquí iría la lógica para añadir una nueva receta
            // Por ahora, mostraremos un mensaje simple
            Toast.makeText(this, "¡Crear nueva receta!", Toast.LENGTH_SHORT).show()
            // Más adelante: Navegar a una Activity para añadir/editar receta
        }
    }

    // --- Implementación de la interfaz OnRecipeClickListener ---
    override fun onRecipeClick(recipe: Recipe) {
        // Lógica cuando se hace clic en una tarjeta de receta completa
        Toast.makeText(this, "Clic en receta: ${recipe.name}", Toast.LENGTH_SHORT).show()
        // Más adelante: Navegar a una Activity de detalles de receta
    }

    override fun onFavoriteClick(recipe: Recipe) {
        // Lógica cuando se hace clic en el icono de favorito
        viewModel.toggleFavoriteStatus(recipe.recipeId)
        val message = if (recipe.isFavorite) "${recipe.name} quitada de favoritos." else "${recipe.name} marcada como favorita."
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}