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
                                  private val listener: (Pokemon) -> Unit): RecyclerView.Adapter<PokemonFavoriteListAdapter.ViewHolder>() {

    class ViewHolder(view: View,
                    val pokemonList: List<Pokemon>,
                    val listener: (Pokemon) -> Unit,
                    val fragment: Fragment): RecyclerView.ViewHolder(view), View.OnClickListener {
        val tviPkFavorite:TextView
        val btnRemove: ImageButton
        val rviPkFavorite: RecyclerView
        /*
        val iviPokemon: ImageView
        val tviPkName: TextView
        val tviPkHp: TextView
        val tviPkAttack: TextView
        val tviPkDefense: TextView
        val tviPkSpAttack: TextView
        val tviPkSpDefense: TextView
        */

        init{
            tviPkFavorite = view.findViewById(R.id.tviPkFavorite);
            btnRemove = view.findViewById(R.id.btnRemove);
            rviPkFavorite = fragment.requireView().findViewById(R.id.rviPkFavorite)

            btnRemove.setOnClickListener{
                val idPokemon = pokemonList[position].id
                val pokemonManager = PokemonManager(it.context)
                pokemonManager.deletePkFav(idPokemon)

                rviPkFavorite.adapter?.notifyDataSetChanged()
                //val ft = (it.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                //ft.replace(R.id.flaMainContent, fragment)
                //ft.remove(fragment)
                //ft.commit()
                Log.i("Delete PK Favorite ->", idPokemon.toString())
/*
                val intent = Intent()
                val bundle = Bundle()
                intent.setClass(view.context, MainActivity::class.java)
                bundle.putString("namefragment", "PokemonsFavoriteFragment")
                intent.putExtra("data",bundle)

                startActivity(view.context,intent,null)

 */
            }
            /*
            iviPokemon = view.findViewById(R.id.iviPokemon);
            tviPkName = view.findViewById(R.id.tviPkName);
            tviPkHp = view.findViewById(R.id.tviPkHp);
            tviPkAttack = view.findViewById(R.id.tviPkAttack);
            tviPkDefense = view.findViewById(R.id.tviPkDefense);
            tviPkSpAttack = view.findViewById(R.id.tviPkSpAttack);
            tviPkSpDefense = view.findViewById(R.id.tviPkSpDefense);
             */
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener(pokemonList[adapterPosition])
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view, pokemonList, listener, fragment)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tviPkFavorite.text = pokemonList[position].name

        /*
        holder.iviPokemon = view.findViewById(R.id.iviPokemon);
        holder.tviPkName = view.findViewById(R.id.tviPkName);
        holder.tviPkHp = view.findViewById(R.id.tviPkHp);
        holder.tviPkAttack = view.findViewById(R.id.tviPkAttack);
        holder.tviPkDefense = view.findViewById(R.id.tviPkDefense);
        holder.tviPkSpAttack = view.findViewById(R.id.tviPkSpAttack);
        holder.tviPkSpDefense = view.findViewById(R.id.tviPkSpDefense);
         */
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
    // Reload current fragment

}