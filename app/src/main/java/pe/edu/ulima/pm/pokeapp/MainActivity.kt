package pe.edu.ulima.pm.pokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import pe.edu.ulima.pm.pokeapp.fragments.PokemonDetailFragment
import pe.edu.ulima.pm.pokeapp.fragments.PokemonsFavoriteFragment
import pe.edu.ulima.pm.pokeapp.fragments.PokemonsFragment
import pe.edu.ulima.pm.pokeapp.model.Pokemon

class MainActivity : AppCompatActivity(),
    PokemonDetailFragment.OnPokemonDetailFragmentListener,
    PokemonsFragment.OnPokemonsFragment {

    private val fragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.add(PokemonsFragment()) // 0
        fragments.add(PokemonsFavoriteFragment()) // 1
        fragments.add(PokemonDetailFragment()) // 2

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.flaMainContent, fragments[0])
        ft.commit()
    }

    override fun onAddFavoriteClick() {
        Log.i("Pokemon Detail Fragment","Se hizo click")
    }

    override fun onAddPokemonItemClick(pokemon: Pokemon) {
        setTitle(pokemon.name)
        val fragment = fragments[2]
        var args = Bundle()
        args.putSerializable("pokemon", pokemon)
        fragment.arguments = args

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaMainContent, fragment)

        ft.commit()
    }
}