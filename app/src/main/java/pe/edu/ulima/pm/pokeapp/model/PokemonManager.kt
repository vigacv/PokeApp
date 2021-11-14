package pe.edu.ulima.pm.pokeapp.model

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pe.edu.ulima.pm.pokeapp.rom.PokeAppDatabase
import pe.edu.ulima.pm.pokeapp.services.PokeAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.*


class PokemonManager(private val context: Context) {

    private val db = Room.databaseBuilder(context, PokeAppDatabase
    ::class.java, "db_pokemons").allowMainThreadQueries().build()

    val API_BASE_URL = "https://pokeapi.co/api/v2/"

    private val dbFirebase = Firebase.firestore

    fun getPokemons(page: Int, callbackOK: (List<Pokemon>) -> Unit, callbackError: (String) -> Unit){
        dbFirebase.collection("pokemon")
            .orderBy("pokeApiId")
            .startAt(1+page*20)
            .endAt(20+page*20)
            .get()
            .addOnSuccessListener { res ->
                val pokemons = arrayListOf<Pokemon>()
                for(document in res){
                    val pokemon = Pokemon(
                        document.data["pokeApiId"].toString().toLong(),
                        document.data["name"].toString(),
                        document.data["hp"].toString().toInt(),
                        document.data["attack"].toString().toInt(),
                        document.data["defense"].toString().toInt(),
                        document.data["spAttack"].toString().toInt(),
                        document.data["spDefense"].toString().toInt(),
                        document.data["imgUrl"].toString(),
                        document.data["isFav"].toString().toBoolean()
                    )
                    pokemons.add(pokemon)
                }
                Log.d("PokemonManager", "Loaded page $page of pokemons from Firebase")
                callbackOK(pokemons)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }

    fun getPokemonsFavorite(): List<Pokemon> {
        return db.pokemonDAO().findFavorites()
    }

    private fun saveIntoRoom(pokemons: List<Pokemon>){
        pokemons.forEach {
            db.pokemonDAO().insert(it)
        }
    }

    fun addPkFav(idPokemon:Long) {
        db.pokemonDAO().updateFav(idPokemon, true)
    }

    fun deletePkFav(idPokemon:Long) {
        db.pokemonDAO().updateFav(idPokemon, false)
    }

    fun isPkFav(idPokemon:Long): Boolean{
        return db.pokemonDAO().isPkFav(idPokemon)
    }

    fun getPokemonsFav(callbackOK: (List<Pokemon>) -> Unit, callbackError: (String) -> Unit){
        try {
            val pokemonsRoom = getPokemonsFavorite()
            if(pokemonsRoom.isNotEmpty()){
                println("Loading pokemons from Room...")
                println(pokemonsRoom)
                callbackOK(pokemonsRoom)
            }
        }catch (exception: Exception){
            exception.message?.let { callbackError(it) }
        }
    }
}