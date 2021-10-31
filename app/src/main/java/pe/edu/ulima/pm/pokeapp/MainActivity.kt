package pe.edu.ulima.pm.pokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import pe.edu.ulima.pm.pokeapp.fragments.PokemonsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var fragments: List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments = mutableListOf(PokemonsFragment())

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.flaMainContent, fragments[0])
        ft.commit()
    }
}