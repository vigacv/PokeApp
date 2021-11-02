package pe.edu.ulima.pm.pokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import pe.edu.ulima.pm.pokeapp.adapter.PokemonFavoriteListAdapter
import pe.edu.ulima.pm.pokeapp.fragments.PokemonDetailFragment
import pe.edu.ulima.pm.pokeapp.fragments.PokemonsFavoriteFragment
import pe.edu.ulima.pm.pokeapp.fragments.PokemonsFragment
import pe.edu.ulima.pm.pokeapp.model.Pokemon

class MainActivity : AppCompatActivity(),
    PokemonDetailFragment.OnPokemonDetailFragmentListener,
    PokemonsFragment.OnPokemonsFragment,
    PokemonsFavoriteFragment.OnPokemonsFavoriteFragment {

    private val fragments = mutableListOf<Fragment>()
    private var nameFragment:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentData:Bundle? = intent.getBundleExtra("data")
        nameFragment = intentData?.getString("namefragment")

        fragments.add(PokemonsFragment()) // 0
        fragments.add(PokemonsFavoriteFragment()) // 1
        fragments.add(PokemonDetailFragment()) // 2

        val ft = supportFragmentManager.beginTransaction()
        if(nameFragment == "PokemonsFragment") {
            setTitle("Pokemones")
            ft.add(R.id.flaMainContent, fragments[0])
        }else if(nameFragment == "PokemonsFavoriteFragment"){
            setTitle("Favoritos")
            ft.add(R.id.flaMainContent, fragments[1])
        }
        ft.commit()
    }

    override fun onAddFavoriteClick() {
        setTitle("Pokemones")
        val fragment = fragments[0]

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaMainContent, fragment)

        ft.commit()
    }

    override fun onPokemonItemClick(pokemon: Pokemon) {
        setTitle(pokemon.name)
        val fragment = fragments[2]
        var args = Bundle()
        args.putSerializable("pokemon", pokemon)
        fragment.arguments = args

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaMainContent, fragment)

        ft.commit()
    }

    override fun onPokemonFavoriteItemClick(pokemon: Pokemon) {
        setTitle(pokemon.name)
        val fragment = fragments[2]
        var args = Bundle()
        args.putSerializable("pokemon", pokemon)
        fragment.arguments = args

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaMainContent, fragment)

        ft.commit()
    }

    override fun Ga() {
        Log.i("Test","ga")
    }

}