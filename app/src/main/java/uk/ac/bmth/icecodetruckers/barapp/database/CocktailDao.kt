package uk.ac.bmth.icecodetruckers.barapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CocktailDao {

    data class InventoryTuple(
        val id: Int,
        val name: String
    )

    @Query("SELECT inventories.id, ingredients.name FROM inventories INNER JOIN ingredients ON inventories.ingredientId=ingredients.id")
    fun getEntireInventory(): LiveData<List<InventoryTuple>>

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

//    @Transaction
//    @Query ("SELECT ingredients.id, ingredients.name FROM ingredients" +
//            "JOIN ingredientsFts ON (ingredients.id = ingredientsFts.id) WHERE ingredientsFts MATCH :term")
//    fun searchIngredients(term: String): List<IngredientFts>

    //TODO Add other deletes
    @Query("DELETE FROM inventories")
    fun deleteAllInventory()
}