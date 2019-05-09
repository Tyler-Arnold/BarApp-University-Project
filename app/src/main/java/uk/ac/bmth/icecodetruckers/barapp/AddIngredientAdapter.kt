package uk.ac.bmth.icecodetruckers.barapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import uk.ac.bmth.icecodetruckers.barapp.database.*

class AddIngredientAdapter internal constructor (
    context: Context,
    var cocktailViewModel: CocktailViewModel
) : RecyclerView.Adapter<AddIngredientAdapter.AddViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var ingredients = emptyList<Ingredient>()


    inner class AddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cocktailItemView: TextView = itemView.findViewById(R.id.addtextView)
        val cocktailAddButton: Button = itemView.findViewById(R.id.addbutton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_itemadd, parent, false)
        return AddViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AddViewHolder, position: Int) {
        val current = ingredients[position]
        holder.cocktailItemView.text = current.name
        val cocktailId = current.id
        println(cocktailId)

        holder.cocktailAddButton.setOnClickListener {
            val entireInventory = cocktailViewModel.getEntireInventory()
            var nextId: Int
            nextId = if (entireInventory.isNullOrEmpty()) {
                0 // There's no inventory, ID should be 0
            } else {
                entireInventory.last().id + 1
            }
            val inventory = Inventory(nextId, 0, cocktailId)
            cocktailViewModel.insert(inventory)
        }
    }

    internal fun setIngredients(ingredients: List<Ingredient>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

    override fun getItemCount() = ingredients.size
}