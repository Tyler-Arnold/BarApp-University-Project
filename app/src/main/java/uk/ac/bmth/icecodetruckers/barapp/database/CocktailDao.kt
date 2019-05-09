package uk.ac.bmth.icecodetruckers.barapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CocktailDao {

    data class InventoryTuple(
        val id: Int,
        val userId: Int,
        val ingredientId: Int,
        val name: String
    )

    @Query("SELECT inventories.id, inventories.userId, inventories.ingredientId, ingredients.name FROM inventories INNER JOIN ingredients ON inventories.ingredientId=ingredients.id")
    fun getEntireInventoryLiveData(): LiveData<List<InventoryTuple>>

    @Query("SELECT inventories.id, inventories.userId, inventories.ingredientId, ingredients.name FROM inventories INNER JOIN ingredients ON inventories.ingredientId=ingredients.id")
    fun getEntireInventory(): List<InventoryTuple>

    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<Product>

    @Query("SELECT * FROM cocktails")
    suspend fun getAllCocktails(): List<Cocktail>

    @Query("SELECT * FROM ingredients_in_cocktail")
    suspend fun getAllIngredientsInCocktail(): List<IngredientsInCocktail>

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

    @Insert
    suspend fun insert(ingredientsInCocktail: IngredientsInCocktail)

//    @Transaction
//    @Query ("SELECT ingredients.id, ingredients.name FROM ingredients" +
//            "JOIN ingredientsFts ON (ingredients.id = ingredientsFts.id) WHERE ingredientsFts MATCH :term")
//    fun searchIngredients(term: String): List<IngredientFts>

    //TODO Add other deletes
    @Query("DELETE FROM users")
    fun deleteAllUser()
    @Query("DELETE FROM inventories")
    fun deleteAllInventory()
    @Query("DELETE FROM ingredients")
    fun deleteAllIngredient()
    @Query("DELETE FROM ingredient_product_link")
    fun deleteAllIngredientProductLink()
    @Query("DELETE FROM products")
    fun deleteAllProduct()
    @Query("DELETE FROM cocktails")
    fun deleteAllCocktail()
    @Query("DELETE FROM ingredients_in_cocktail")
    fun deleteAllIngredientsInCocktail()

    @Delete
    fun delete(inventory: Inventory)
}