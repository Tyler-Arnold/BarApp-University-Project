package uk.ac.bmth.icecodetruckers.barapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.ac.bmth.icecodetruckers.barapp.database.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BarManagement.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BarManagement.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BarManagement : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var cocktailViewModel: CocktailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_bar_management, container, false)

        //get the view model, so I can do database stuff from here, even though I shouldn't
        cocktailViewModel = ViewModelProviders.of(this).get(CocktailViewModel::class.java)

        //set up the recycler view
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CocktailInventoryListAdapter(this.activity!!.applicationContext, cocktailViewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.activity!!.applicationContext)

        //observe the inventory LiveData
        cocktailViewModel.entireInventory.observe(this, Observer {
                inventories -> // Update the cached copy of the words in the adapter.
            inventories?.let { adapter.setInventories(it) }



            calculateProduceableCocktails(cocktailViewModel.getAllCocktails(), cocktailViewModel.getAllIngredientsInCocktail(), inventories)
        })

        return view
    }

    fun calculateProduceableCocktails(cocktails: List<Cocktail>, ingredientsInCocktail: List<IngredientsInCocktail>, inventory: List<CocktailDao.InventoryTuple>): List<Cocktail> {
        var producibleCocktails: List<Cocktail> = mutableListOf()

        var currentCocktail = 0
        var numberOfIngredients = 0
        var numberOfMatchingIngredients = 0
        for(ingredientIC in ingredientsInCocktail) {
            if (currentCocktail != ingredientIC.cocktailId) {
                //new cocktail
                if (numberOfIngredients==numberOfMatchingIngredients) {
                    //last cocktail can be made
                    var newCocktail = cocktails.find {it.id == currentCocktail}

                    if (newCocktail!= null) {
                        //cocktail actually exists (final null check)
                        producibleCocktails += newCocktail //add cocktail to list
                    }
                }

                //reset variables for new cocktail
                currentCocktail = ingredientIC.cocktailId
                numberOfMatchingIngredients = 0
                numberOfIngredients = 0
            }


            if ((inventory.find {it.ingredientId == ingredientIC.ingredientId}) != null) { //if you have current ingredient
                numberOfMatchingIngredients++
            }

            numberOfIngredients++ //count the number of ingredients in this cocktail
        }

        return producibleCocktails
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BarManagement.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BarManagement().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
