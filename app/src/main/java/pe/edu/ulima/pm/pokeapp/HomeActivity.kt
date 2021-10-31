package pe.edu.ulima.pm.pokeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.btnContinuar).setOnClickListener{
            changeActivity()
        }
    }

    private fun changeActivity() {
        val intent: Intent = Intent()
        intent.setClass(this, MainActivity::class.java)

        startActivity(intent)
    }
}