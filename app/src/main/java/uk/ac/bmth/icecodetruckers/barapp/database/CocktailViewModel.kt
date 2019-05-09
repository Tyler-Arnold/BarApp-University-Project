package uk.ac.bmth.icecodetruckers.barapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CocktailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CocktailRepository
    //val allWords: LiveData<List<Word>>
    val entireInventory: LiveData<List<CocktailDao.InventoryTuple>>
    val allingredients: LiveData<List<Ingredient>>

    init {
        //database retrieval
        val cocktailDao = CocktailRoomDatabase.getDatabase(application, viewModelScope).cocktailDao()
        repository = CocktailRepository(cocktailDao)
        //allWords = repository.allWords
        entireInventory = repository.entireInventory

        allingredients = repository.allingredients


    }

    fun getEntireInventory(): List<CocktailDao.InventoryTuple> {
        return runBlocking {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                repository.getEntireInventory()
            }
        }
    }

    fun getAllCocktails(): List<Cocktail> {
        return runBlocking {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                repository.getCocktails()
            }
        }
    }

    fun getAllIngredientsInCocktail(): List<IngredientsInCocktail> {
        return runBlocking {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                repository.getIngredientsInCocktail()
            }
        }
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

    fun delete(inventory: Inventory) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(inventory)
    }

//    fun searchIngredients(term: String): List<IngredientFts> {
//        return repository.searchIngredients(term)
//    }
}