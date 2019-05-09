package uk.ac.bmth.icecodetruckers.barapp;

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.ac.bmth.icecodetruckers.barapp.database.*



class AddIngredient : AppCompatActivity(),
    MainPage.OnFragmentInteractionListener,
    BarManagement.OnFragmentInteractionListener,
    CocktailCalculator.OnFragmentInteractionListener,
    CocktailRecipe.OnFragmentInteractionListener {


    private lateinit var navController: NavController
    private lateinit var cocktailViewModel: CocktailViewModel

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addingredient)
        cocktailViewModel = ViewModelProviders.of(this).get(CocktailViewModel::class.java)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewadd)
        val adapter = AddIngredientAdapter(this,cocktailViewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        cocktailViewModel.allingredients.observe(this, Observer { ingredients ->
            ingredients?.let { adapter.setIngredients(it) }
        })
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}
