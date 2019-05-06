package uk.ac.bmth.icecodetruckers.barapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CocktailDao {

    @Query("SELECT * FROM inventories")
    fun getEntireInventory(): List<Inventory>

    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): List<Ingredient>

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<Product>

    @Insert
    suspend fun insert(user: User)

    @Insert
    suspend fun insert(inventory: Inventory)

    @Insert
    suspend fun insert(ingredient: Ingredient)

    @Insert
    suspend fun insert(ingredientProductLink: IngredientProductLink)

    @Insert
    suspend fun insert(product: Product)

    @Insert
    suspend fun insert(cocktail: Cocktail)

    //TODO Add other deletes
    @Query("DELETE FROM inventories")
    fun deleteAllInventory()
}