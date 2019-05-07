package uk.ac.bmth.icecodetruckers.barapp

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import uk.ac.bmth.icecodetruckers.barapp.database.CocktailViewModel

class IngredientSearchableActivity : AppCompatActivity() {

    private lateinit var cocktailViewModel: CocktailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_searchable)

        cocktailViewModel = ViewModelProviders.of(this).get(CocktailViewModel::class.java)

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
            }
        }

    }

    private fun doMySearch(query: String) {
        //cocktailViewModel.searchIngredients(query)
    }
}
