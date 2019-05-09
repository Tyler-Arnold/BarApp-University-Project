package uk.ac.bmth.icecodetruckers.barapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(User::class, Inventory::class, Ingredient::class, IngredientProductLink::class, Product::class, Cocktail::class, IngredientsInCocktail::class), version = 4)
public abstract class CocktailRoomDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao

    companion object {
        @Volatile
        private var INSTANCE: CocktailRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): CocktailRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CocktailRoomDatabase::class.java, "Word_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }

//        private val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `ingredientsFts` USING FTS4(`name`, content=`ingredients`)")
//                database.execSQL("INSERT INTO ingredientsFts(ingredientsFts) VALUES ('rebuild')")
//            }
//        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.cocktailDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(cocktailDao: CocktailDao) {
            cocktailDao.deleteAllUser()
            cocktailDao.deleteAllInventory()
            cocktailDao.deleteAllIngredient()
            cocktailDao.deleteAllIngredientProductLink()
            cocktailDao.deleteAllProduct()
            cocktailDao.deleteAllCocktail()
            cocktailDao.deleteAllIngredientsInCocktail()

            var user = User(0, "Test User", "nopass")
            cocktailDao.insert(user)

            var ingredient = Ingredient(0, "White Rum")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(1, "Dark Rum")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(2, "Simple Syrup")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(3, "Lime Juice")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(4, "Gin")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(5, "Tonic")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(6, "Lemon Juice")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(7, "Soda Water")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(8, "Cola")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(9, "Ginger Beer")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(10, "Vodka")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(11, "Whiskey")
            cocktailDao.insert(ingredient)
            ingredient = Ingredient(12, "Bitters")
            cocktailDao.insert(ingredient)

//            var inventory = Inventory(0, 0, 0)
//            cocktailDao.insert(inventory)
//            inventory = Inventory(1, 0, 1)
//            cocktailDao.insert(inventory)
//            inventory = Inventory(2, 0, 2)
//            cocktailDao.insert(inventory)
//            inventory = Inventory(3, 0, 3)
//            cocktailDao.insert(inventory)
//            inventory = Inventory(4, 0, 3)
//            cocktailDao.insert(inventory)

            var product = Product(0, "Captain Morgan's White Rum", 15.99, "https://www.example.com/")
            cocktailDao.insert(product)

            var ingredientProductLink = IngredientProductLink(0, 0, 0)
            cocktailDao.insert(ingredientProductLink)

            var cocktail = Cocktail(0, "Daiquiri", "Sweet, sour, refreshing",
                "45ml White Rum \n25ml Simple Syrup \n30ml Lime Juice",
                "1. Pour all ingredients into shaker full of ice\n" +
                        "2. Shake well for 10 seconds\n" +
                        "3. Double-strain into a chilled cocktail glass",
                "none")
            cocktailDao.insert(cocktail)
            cocktail = Cocktail(1, "Gin & Tonic", "The classic",
                "60ml Gin \n120ml Tonic",
                "1. Pour all ingredients into glass with ice",
                "none")
            cocktailDao.insert(cocktail)
            cocktail = Cocktail(2, "Tom Collins", "Citrus-y and refreshing",
                "45ml Gin \n20ml Simple Syrup \n30ml Lemon Juice\n" +
                        "60ml Soda Water",
                "1. Pour gin, lemon juice, and sugar syrup into shaker with ice\n" +
                        "2. Shake well for 10 seconds\n" +
                        "3. Double-strain into a chilled collins glass full of ice\n" +
                        "4. Top with soda water",
                "none")
            cocktailDao.insert(cocktail)
            cocktail = Cocktail(3, "Cuba Libre", "The classic rum and coke, with a twist",
                "60ml White Rum \n30ml Lime Juice \nCola",
                "1. Pour all ingredients into glass with ice",
                "none")
            cocktailDao.insert(cocktail)
            cocktail = Cocktail(4, "Dark & Stormy", "Caribbean classic",
            "45ml Dark Rum \nGinger Beer \nBitters",
            "1. Pour all ingredients into glass full of ice, add a dash of bitters",
            "none")
            cocktailDao.insert(cocktail)
            cocktail = Cocktail(5, "Vodka Soda", "A blank canvas",
                "45ml Vodka \nSoda Water",
                "1. Pour all ingredients into glass full of ice",
                "none")
            cocktailDao.insert(cocktail)

            var ingredientInCocktail = IngredientsInCocktail(0, 0, 0) // Daiquiri (my favourite)
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(1, 0, 2)
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(2, 0, 3)
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(3, 1, 4) // Gin & Tonic
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(4, 1, 5)
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(5, 2, 2) // Tom Collins
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(6, 2, 4)
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(7, 2, 6)
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(8, 2, 7)
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(9, 3, 0) // Cuba Libre
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(10, 3, 3)
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(11, 3, 8)
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(12, 4, 1) // Dark & Stormy
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(13, 4, 9)
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(14, 5, 7) // Vodka Soda
            cocktailDao.insert(ingredientInCocktail)
            ingredientInCocktail = IngredientsInCocktail(15, 5, 10)
            cocktailDao.insert(ingredientInCocktail)
        }


    }
}