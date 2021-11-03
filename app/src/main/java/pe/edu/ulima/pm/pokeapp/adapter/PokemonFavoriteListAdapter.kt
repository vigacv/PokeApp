package pe.edu.ulima.pm.pokeapp.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.pokeapp.MainActivity
import pe.edu.ulima.pm.pokeapp.R
import pe.edu.ulima.pm.pokeapp.model.Pokemon
import pe.edu.ulima.pm.pokeapp.model.PokemonManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import pe.edu.ulima.pm.pokeapp.fragments.PokemonsFavoriteFragment


class PokemonFavoriteListAdapter (private val fragment: Fragment,
                                  val pokemonList: MutableList<Pokemon>,
                                  val funRemove: (Long) -> Unit,
                                  private val listener: (Pokemon) -> Unit): RecyclerView.Adapter<PokemonFavoriteListAdapter.ViewHolder>() {

    class ViewHolder(view: View,
                    val pokemonList: List<Pokemon>,
                    val listener: (Pokemon) -> Unit,
                    val funRemove: (Long) -> Unit): RecyclerView.ViewHolder(view), View.OnClickListener {
        val tviPkFavorite:TextView
        val btnRemove: ImageButton

        init{
            tviPkFavorite = view.findViewById(R.id.tviPkFavorite);
            btnRemove = view.findViewById(R.id.btnRemove);

            btnRemove.setOnClickListener{
                val idPokemon = pokemonList[position].id
                funRemove(idPokemon)
            }
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener(pokemonList[adapterPosition])
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view, pokemonList, listener, funRemove)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tviPkFavorite.text = pokemonList[position].name
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}