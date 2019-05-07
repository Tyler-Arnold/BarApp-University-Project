package uk.ac.bmth.icecodetruckers.barapp.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class CocktailRepository(private val cocktailDao: CocktailDao) {
    //val allWords: LiveData<List<Word>> = wordDao.getAllWords()
    var entireInventory: LiveData<List<CocktailDao.InventoryTuple>> = cocktailDao.getEntireInventory()

    //wrapper for insert method, must call on non-ui thread or app will crash
    //use @WorkerThread to mark the method needs to be called on a non-ui thread
    //add suspend modifier to tell compiler that this needs to be called from a coroutine or suspending function
    @WorkerThread
    suspend fun insert(user: User) {
        cocktailDao.insert(user)
    }

    @WorkerThread
    suspend fun insert(inventory: Inventory) {
        cocktailDao.insert(inventory)
    }

    @WorkerThread
    suspend fun insert(ingredient: Ingredient) {
        cocktailDao.insert(ingredient)
    }

    @WorkerThread
    suspend fun insert(ingredientProductLink: IngredientProductLink) {
        cocktailDao.insert(ingredientProductLink)
    }

    @WorkerThread
    suspend fun insert(product: Product) {
        cocktailDao.insert(product)
    }

    @WorkerThread
    suspend fun insert(cocktail: Cocktail) {
        cocktailDao.insert(cocktail)
    }

//    @WorkerThread
//    fun searchIngredients(term: String): List<IngredientFts> {
//        return cocktailDao.searchIngredients(term)
//    }
}