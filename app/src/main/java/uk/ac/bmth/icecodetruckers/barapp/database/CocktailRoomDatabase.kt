package uk.ac.bmth.icecodetruckers.barapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(User::class, Inventory::class, Ingredient::class, IngredientProductLink::class, Product::class, Cocktail::class), version = 1)
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
                    CocktailRoomDatabase::class.java,
                    "Word_database"
                ).addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }

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
            cocktailDao.deleteAllInventory()

            var user = User(0, "Test User", "nopass")
            cocktailDao.insert(user)

            var ingredient = Ingredient(0, "White Rum")
            cocktailDao.insert(ingredient)

            var inventory = Inventory(0, 0, 0)
            cocktailDao.insert(inventory)

            var product = Product(0, "Captain Morgan's White Rum", 15.99, "https://www.example.com/")
            cocktailDao.insert(product)

            var ingredientProductLink = IngredientProductLink(0, 0, 0)
            cocktailDao.insert(ingredientProductLink)
        }


    }
}