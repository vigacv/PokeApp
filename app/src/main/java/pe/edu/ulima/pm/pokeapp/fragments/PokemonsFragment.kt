package pe.edu.ulima.pm.pokeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import pe.edu.ulima.pm.pokeapp.R
import pe.edu.ulima.pm.pokeapp.adapter.PokemonListAdapter
import pe.edu.ulima.pm.pokeapp.model.PokeApiResult
import pe.edu.ulima.pm.pokeapp.model.Pokemon
import pe.edu.ulima.pm.pokeapp.model.PokemonManager

class PokemonsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PokemonManager(requireActivity().applicationContext).getPokemonsRetrofit({pkList: List<Pokemon> ->
            val rviPokemon = view.findViewById<RecyclerView>(R.id.rviPokemons)

            println("Pokemon list loaded")

            rviPokemon.adapter = PokemonListAdapter(this, pkList){ pokemon: Pokemon ->
                println("Test")
            }
        }, {error ->
            println("Error: $error")
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })
    }
}