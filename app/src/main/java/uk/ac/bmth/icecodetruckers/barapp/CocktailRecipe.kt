package uk.ac.bmth.icecodetruckers.barapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import uk.ac.bmth.icecodetruckers.barapp.database.CocktailViewModel


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_COCKTAIL = "COCKTAIL"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CocktailRecipe.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CocktailRecipe.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CocktailRecipe : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var cocktailViewModel: CocktailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_COCKTAIL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_cocktail_recipe, container, false)

        //get the view model, so I can do database stuff from here, even though I shouldn't
        cocktailViewModel = ViewModelProviders.of(this).get(CocktailViewModel::class.java)

        var textCocktailName = view.findViewById<TextView>(R.id.textViewCocktailName)
        var textCocktailIngredients = view.findViewById<TextView>(R.id.textViewCocktailIngredientsList)
        var textCocktailRecipe = view.findViewById<TextView>(R.id.textViewCocktailRecipeBody)

        var cocktail = cocktailViewModel.getAllCocktails().find{ it.id == param1}

        if (cocktail != null) {
            textCocktailName.text = cocktail.name
            textCocktailIngredients.text = cocktail.ingredients
            textCocktailRecipe.text = cocktail.recipe
        }

        return view
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
         * @return A new instance of fragment CocktailRecipe.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            CocktailRecipe().apply {
                arguments = Bundle().apply {
                    putString(ARG_COCKTAIL, param1)
                }
            }
    }
}
