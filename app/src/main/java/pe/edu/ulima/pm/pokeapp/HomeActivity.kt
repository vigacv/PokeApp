package pe.edu.ulima.pm.pokeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlin.system.exitProcess

class HomeActivity: AppCompatActivity() {

    private val dbFirebase = Firebase.firestore

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
            saveUser(findViewById<EditText>(R.id.eteName).text.toString())
        }

        findViewById<Button>(R.id.btnFavoritos).setOnClickListener{
            changeActivity("PokemonsFavoriteFragment")
        }
    }

    private fun saveUser(username: String){
        dbFirebase.collection("users")
            .whereEqualTo("name", username)
            .get()
            .addOnSuccessListener { documents ->
                if(documents.isEmpty){
                    val data = hashMapOf<String, String>("name" to username)
                    dbFirebase.collection("users")
                        .add(data)
                        .addOnSuccessListener {
                            Log.d("HomeActivity", "Document created successfully")
                        }
                        .addOnFailureListener {
                            Log.d("HomeActivity", "Error creating document")
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("HomeActivity", "Error getting documents: ", exception)
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