package uk.ac.bmth.icecodetruckers.barapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.ac.bmth.icecodetruckers.barapp.database.*

class CocktailInventoryListAdapter internal constructor (
    context: Context,
    var cocktailViewModel: CocktailViewModel
) : RecyclerView.Adapter<CocktailInventoryListAdapter.CocktailViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var inventories = emptyList<CocktailDao.InventoryTuple>() // Cached copy of ingredients

    inner class CocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cocktailItemView: TextView = itemView.findViewById(R.id.textView)
        val cocktailRemoveButton: Button = itemView.findViewById(R.id.button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_button, parent, false)
        return CocktailViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val current = inventories[position]
        holder.cocktailItemView.text = current.name


        // Remove an Item
        // I know this shouldn't be in the adapter, but I don't care anymore
        holder.cocktailRemoveButton.setOnClickListener {
            val inventory = Inventory(current.id, current.userId, current.ingredientId)
            cocktailViewModel.delete(inventory)
        }
    }

    internal fun setInventories(inventories: List<CocktailDao.InventoryTuple>) {
        this.inventories = inventories
        notifyDataSetChanged()
    }

    override fun getItemCount() = inventories.size
}