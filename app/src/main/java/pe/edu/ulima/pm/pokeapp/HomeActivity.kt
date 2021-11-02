package pe.edu.ulima.pm.pokeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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