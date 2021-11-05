package pe.edu.ulima.pm.pokeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
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
        title = "PokeApp"
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                when {
                    fragments[0].isVisible -> {
                        title = "PokeApp"
                    }
                    fragments[1].isVisible -> {
                        title = "PokeApp"
                    }
                    fragments[2].isVisible -> {

                        if(supportFragmentManager.getBackStackEntryAt(0).name == "stackA"){
                            title = "Pokemones"
                        }else if(supportFragmentManager.getBackStackEntryAt(0).name == "stackB"){
                            title = "Favoritos"
                        }

                    }
                    else -> {
                        title = "PokeApp"
                    }
                }
                if(fragments[0].isVisible or fragments[1].isVisible){
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)
                }else{
                    if(supportFragmentManager.getBackStackEntryAt(0).name == "stackA"){
                        title = "Pokemones"
                    }else if(supportFragmentManager.getBackStackEntryAt(0).name == "stackB"){
                        title = "Favoritos"
                    }
                    isEnabled = false
                    onBackPressed()
                }

            }

        })
        setContentView(R.layout.activity_main)

        val intentData:Bundle? = intent.getBundleExtra("data")
        nameFragment = intentData?.getString("namefragment")

        fragments.add(PokemonsFragment()) // 0
        fragments.add(PokemonsFavoriteFragment()) // 1
        fragments.add(PokemonDetailFragment()) // 2

        val ft = supportFragmentManager.beginTransaction()
        if(nameFragment == "PokemonsFragment") {
            setTitle("Pokemones")
            ft.add(R.id.flaMainContent, fragments[0]).disallowAddToBackStack()
        }else if(nameFragment == "PokemonsFavoriteFragment"){
            setTitle("Favoritos")
            ft.add(R.id.flaMainContent, fragments[1]).disallowAddToBackStack()
        }
        ft.commit()
    }


    override fun onAddFavoriteClick() {
        setTitle("Pokemones")
        supportFragmentManager.popBackStack()
        val fragment = fragments[0]

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaMainContent, fragment).disallowAddToBackStack()

        ft.commit()

    }

    override fun onPokemonItemClick(pokemon: Pokemon) {
        setTitle(pokemon.name)
        val fragment = fragments[2]
        var args = Bundle()
        args.putSerializable("pokemon", pokemon)
        fragment.arguments = args

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaMainContent, fragment).addToBackStack("stackA")
        ft.commit()
    }

    override fun onPokemonFavoriteItemClick(pokemon: Pokemon) {
        setTitle(pokemon.name)
        val fragment = fragments[2]
        var args = Bundle()
        args.putSerializable("pokemon", pokemon)
        fragment.arguments = args

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaMainContent, fragment).addToBackStack("stackB")
        ft.commit()
    }

}