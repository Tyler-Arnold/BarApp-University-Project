package uk.ac.bmth.icecodetruckers.barapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import uk.ac.bmth.icecodetruckers.barapp.database.*

class CocktailYourCocktailListAdapter internal constructor (
    context: Context,
    var cocktailViewModel: CocktailViewModel,
    var navController: NavController
) : RecyclerView.Adapter<CocktailYourCocktailListAdapter.CocktailViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cocktails = emptyList<Cocktail>() // Cached copy of ingredients

    inner class CocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cocktailItemView: TextView = itemView.findViewById(R.id.textViewMainPageTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_no_button, parent, false)
        return CocktailViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val current = cocktails[position]
        holder.cocktailItemView.text = current.name

        holder.cocktailItemView.setOnClickListener {
            val cocktailId = current.id
            var bundle: Bundle = Bundle()
            bundle.putInt("COCKTAIL", cocktailId)
            if (navController.currentDestination!!.id == R.id.barManagement){
                navController.navigate(R.id.action_barManagement_to_cocktailRecipe, bundle)
            } else {
                navController.navigate(R.id.action_mainPage_to_cocktailRecipe, bundle)
            }
        }

    }

    internal fun setCocktails(cocktails: List<Cocktail>) {
        this.cocktails = cocktails
        notifyDataSetChanged()
    }

    override fun getItemCount() = cocktails.size
}