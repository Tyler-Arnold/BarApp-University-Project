package uk.ac.bmth.icecodetruckers.barapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavouriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavouritesRepository
    //val allWords: LiveData<List<Word>>
    val entireFavourite: LiveData<List<CocktailDao.FavouriteTuple>>

    init {

        //database retrieval
        val cocktailDao = CocktailRoomDatabase.getDatabase(application, viewModelScope).cocktailDao()
        repository = FavouritesRepository(cocktailDao)
        //allWords = repository.allWords
        entireFavourite = repository.entireFavourite
    }

    //create a wrapper insert method that calls the repository's insert method.
    //to avoid calling on main thread, we launch a new coroutine based on the scope defined previously

    fun delete(favourite: Favourite) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(favourite)
    }


//    fun searchIngredients(term: String): List<IngredientFts> {
//        return repository.searchIngredients(term)
//    }
}