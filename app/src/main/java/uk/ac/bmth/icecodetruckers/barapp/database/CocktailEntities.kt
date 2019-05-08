package uk.ac.bmth.icecodetruckers.barapp.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Fts4
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo



@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val username: String,
    val passwordHash: String
)

@Entity(tableName = "inventories")
data class Inventory(
    @PrimaryKey val id: Int,
    val userId: Int,
    val ingredientId: Int
)

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey val id: Int,
    val name: String
)

@Entity(tableName = "ingredient_product_link")
data class IngredientProductLink(
    @PrimaryKey val id: Int,
    val ingredientId: Int,
    val productId: Int
)

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val URL: String
)

@Entity(tableName = "cocktails")
data class Cocktail(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val recipe: String,
    val imagePath: String
)

@Entity(tableName = "favourites")
data class Favourite(
    @PrimaryKey val id: Int,
    val name: String
)

//
//@Fts4(contentEntity = Ingredient::class)
//@Entity(tableName = "ingredientsFts")
//class IngredientFts(@PrimaryKey private val rowid: Int,
//                    var name: String) {
//
//}

