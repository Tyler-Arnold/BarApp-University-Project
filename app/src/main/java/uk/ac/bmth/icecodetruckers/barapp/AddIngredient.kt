package uk.ac.bmth.icecodetruckers.barapp;

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import uk.ac.bmth.icecodetruckers.barapp.database.*



class AddIngredient : AppCompatActivity(),
    MainPage.OnFragmentInteractionListener,
    BarManagement.OnFragmentInteractionListener,
    CocktailCalculator.OnFragmentInteractionListener,
    CocktailRecipe.OnFragmentInteractionListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController


    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addingredient)
    }

     suspend fun confirmAdd(cocktailDao:CocktailDao,view: View){
        val inputtext= findViewById<EditText>(R.id.ingredientName)
        var user = User(0, "Test User", "nopass")
        cocktailDao.insert(user)

         var ingredient = Ingredient(1,inputtext.text.toString())
        cocktailDao.insert(ingredient)
         finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}
