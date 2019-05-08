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

    data class FavouriteTuple(
        val id: Int,
        val name: String
    )

    @Query("SELECT inventories.id, inventories.userId, inventories.ingredientId, ingredients.name FROM inventories INNER JOIN ingredients ON inventories.ingredientId=ingredients.id")
    fun getEntireInventory(): LiveData<List<InventoryTuple>>

    @Query("SELECT favourites.id, favourites.name FROM favourites")
    fun getEntireFavourite(): LiveData<List<FavouriteTuple>>

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

    @Insert
    suspend fun insert(favourite: Favourite)

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

    @Query( "DELETE FROM favourites")
    fun deleteAllFavourites()

    @Delete
    fun deleteInv(inventory: Inventory)

    @Delete
    fun deleteFav(favourite: Favourite)
}