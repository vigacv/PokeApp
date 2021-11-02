package pe.edu.ulima.pm.pokeapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.pokeapp.R
import pe.edu.ulima.pm.pokeapp.model.Pokemon
import pe.edu.ulima.pm.pokeapp.model.PokemonManager

class PokemonDetailFragment: Fragment() {
    interface OnPokemonDetailFragmentListener{
        fun onAddFavoriteClick()
    }

    private var listener: OnPokemonDetailFragmentListener? = null

    override fun onAttach(context: Context){
        super.onAttach(context)
        listener = context as? OnPokemonDetailFragmentListener
    }

    private var pokemon:Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pokemon = arguments?.getSerializable("pokemon") as Pokemon
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnAddFavorite = view.findViewById<Button>(R.id.btnAddFavorite)
        setPokemonDetail(view, pokemon!!)

        btnAddFavorite.setOnClickListener{
            listener?.onAddFavoriteClick()
        }
    }

    private fun setPokemonDetail(view: View, pokemon: Pokemon){
        var img = view.findViewById<ImageView>(R.id.iviPokemon)
        Glide.with(this).load(pokemon.imgUrl).into(img)
        view.findViewById<TextView>(R.id.tviPkHp).text = pokemon.hp.toString()
        view.findViewById<TextView>(R.id.tviPkAttack).text = pokemon.attack.toString()
        view.findViewById<TextView>(R.id.tviPkDefense).text = pokemon.defense.toString()
        view.findViewById<TextView>(R.id.tviPkSpAttack).text = pokemon.spAttack.toString()
        view.findViewById<TextView>(R.id.tviPkSpDefense).text = pokemon.spDefense.toString()
    }
}