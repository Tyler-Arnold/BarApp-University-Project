package uk.ac.bmth.icecodetruckers.barapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CocktailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CocktailRepository
    //val allWords: LiveData<List<Word>>

    init {
        //database retrieval
        val cocktailDao = CocktailRoomDatabase.getDatabase(application, viewModelScope).cocktailDao()
        repository = CocktailRepository(cocktailDao)
        //allWords = repository.allWords
    }

    //create a wrapper insert method that calls the repository's insert method.
    //to avoid calling on main thread, we launch a new coroutine based on the scope defined previously
    fun insert(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(user)
    }

    fun insert(inventory: Inventory) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(inventory)
    }

    fun insert(ingredient: Ingredient) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(ingredient)
    }

    fun insert(ingredientProductLink: IngredientProductLink) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(ingredientProductLink)
    }

    fun insert(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(product)
    }

    fun insert(cocktail: Cocktail) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(cocktail)
    }
}