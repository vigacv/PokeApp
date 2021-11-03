package pe.edu.ulima.pm.pokeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlin.system.exitProcess

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        onBackPressedDispatcher.addCallback(this, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
                exitProcess(0)
            }
        })

        findViewById<Button>(R.id.btnContinuar).setOnClickListener{
            changeActivity("PokemonsFragment")
        }

        findViewById<Button>(R.id.btnFavoritos).setOnClickListener{
            changeActivity("PokemonsFavoriteFragment")
        }
    }

    private fun changeActivity(nameFragment: String) {
        val intent = Intent()
        val bundle = Bundle()
        intent.setClass(this, MainActivity::class.java)
        bundle.putString("namefragment", nameFragment)
        intent.putExtra("data",bundle)
        startActivity(intent)
    }
}