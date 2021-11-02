package pe.edu.ulima.pm.pokeapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.pokeapp.R
import pe.edu.ulima.pm.pokeapp.adapter.PokemonListAdapter
import pe.edu.ulima.pm.pokeapp.model.Pokemon
import pe.edu.ulima.pm.pokeapp.model.PokemonManager

class PokemonsFragment: Fragment() {

    interface OnPokemonsFragment{
        fun onAddPokemonItemClick(pokemon:Pokemon)
    }

    private var listener: OnPokemonsFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnPokemonsFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var pokemonList = mutableListOf<Pokemon>()
        var currentPage = 0

        val rviPokemon = view.findViewById<RecyclerView>(R.id.rviPokemons)
        val pokemonManager = PokemonManager(requireActivity().applicationContext)

        pokemonManager.getPokemons(currentPage,{pkList: List<Pokemon> ->
            pokemonList.addAll(pkList)
            rviPokemon.adapter = PokemonListAdapter(this, pokemonList){ pokemon: Pokemon ->
                println("Test: " + pokemon.name)
                listener?.onAddPokemonItemClick(pokemon)

            }
        }, {error ->
            println("Error: $error")
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })

        rviPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                currentPage++
                if (!recyclerView.canScrollVertically(1)) {
                    pokemonManager.getPokemons(currentPage, {pkList: List<Pokemon> ->
                        pokemonList.addAll(pkList)
                        rviPokemon.adapter?.notifyDataSetChanged()
                    }, {error ->
                        println("Error: $error")
                        Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        })
    }
}