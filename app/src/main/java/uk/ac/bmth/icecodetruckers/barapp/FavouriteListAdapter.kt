package uk.ac.bmth.icecodetruckers.barapp

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.ac.bmth.icecodetruckers.barapp.database.*

class FavouriteListAdapter internal constructor (
    context: Context,
    var favouriteViewModel: FavouriteViewModel
) : RecyclerView.Adapter<FavouriteListAdapter.FavouriteViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var favourites = emptyList<CocktailDao.FavouriteTuple>() // Cached copy of ingredients

    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cocktailItemView: TextView = itemView.findViewById(R.id.textView)
        val cocktailRemoveButton: Button = itemView.findViewById(R.id.button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return FavouriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val current = favourites[position]
        holder.cocktailItemView.text = current.name


        // Remove an Item
        // I know this shouldn't be in the adapter, but I don't care anymore
        holder.cocktailRemoveButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val favourite = Favourite(current.id, current.name)
                favouriteViewModel.delete(favourite)
            }
        })
    }

    internal fun setFavourites(favourits: List<CocktailDao.FavouriteTuple>) {
        this.favourites = favourites
        notifyDataSetChanged()
    }

    override fun getItemCount() = favourites.size
}

