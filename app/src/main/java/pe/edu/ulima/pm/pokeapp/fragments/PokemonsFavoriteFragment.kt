package pe.edu.ulima.pm.pokeapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.pokeapp.R
import pe.edu.ulima.pm.pokeapp.adapter.PokemonFavoriteListAdapter
import pe.edu.ulima.pm.pokeapp.adapter.PokemonListAdapter
import pe.edu.ulima.pm.pokeapp.model.Pokemon
import pe.edu.ulima.pm.pokeapp.model.PokemonManager

class PokemonsFavoriteFragment: Fragment() {

    interface OnPokemonsFavoriteFragment{
        fun onPokemonFavoriteItemClick(pokemon:Pokemon)
    }

    private var listener: OnPokemonsFavoriteFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnPokemonsFavoriteFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemons_favorite, container, false)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().title="Favoritos"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rviPkFavorite = view.findViewById<RecyclerView>(R.id.rviPkFavorite)

        var pkFavList = mutableListOf<Pokemon>()
        val pokemonManager = PokemonManager(requireActivity().applicationContext)

        pokemonManager.getPokemonsFav({pkList: List<Pokemon> ->
            pkFavList.addAll(pkList)
            rviPkFavorite.adapter = PokemonFavoriteListAdapter(this, pkFavList, {
                pokemonManager.deletePkFav(it)
                var idPos = 0
                pkFavList.forEachIndexed { id, pokemon ->
                    if (pokemon.id == it){
                        idPos = id
                    }
                }
                pkFavList.removeAt(idPos)
                rviPkFavorite.adapter?.notifyItemRemoved(idPos)
            }){ pokemon: Pokemon ->
                listener?.onPokemonFavoriteItemClick(pokemon)
            }

        }, {error ->
            println("Error: $error")
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })

    }
}