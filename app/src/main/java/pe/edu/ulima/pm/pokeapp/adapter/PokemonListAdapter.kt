package pe.edu.ulima.pm.pokeapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.pokeapp.R
import pe.edu.ulima.pm.pokeapp.model.Pokemon

class PokemonListAdapter(private val fragment: Fragment,
                         val pokemonList: MutableList<Pokemon>,
                         private val listener: (Pokemon) -> Unit): RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    class ViewHolder(view: View, val pokemonList: List<Pokemon>, val listener: (Pokemon) -> Unit): RecyclerView.ViewHolder(view), View.OnClickListener {
        val iviPokemon: ImageView
        val tviPkName: TextView
        val tviPkHp: TextView
        val tviPkAttack: TextView
        val tviPkDefense: TextView
        val tviPkSpAttack: TextView
        val tviPkSpDefense: TextView

        init{
            iviPokemon = view.findViewById(R.id.iviPokemon);
            tviPkName = view.findViewById(R.id.tviPkName);
            tviPkHp = view.findViewById(R.id.tviPkHp);
            tviPkAttack = view.findViewById(R.id.tviPkAttack);
            tviPkDefense = view.findViewById(R.id.tviPkDefense);
            tviPkSpAttack = view.findViewById(R.id.tviPkSpAttack);
            tviPkSpDefense = view.findViewById(R.id.tviPkSpDefense);
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener(pokemonList[adapterPosition])
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)

        return ViewHolder(view, pokemonList, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tviPkName.text = pokemonList[position].name
        holder.tviPkHp.text = pokemonList[position].hp.toString()
        holder.tviPkName.text = pokemonList[position].name
        holder.tviPkAttack.text = pokemonList[position].attack.toString()
        holder.tviPkDefense.text = pokemonList[position].defense.toString()
        holder.tviPkSpAttack.text = pokemonList[position].spAttack.toString()
        holder.tviPkSpDefense.text = pokemonList[position].spDefense.toString()

        Glide.with(fragment).load(pokemonList[position].imgUrl).into(holder.iviPokemon)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

}