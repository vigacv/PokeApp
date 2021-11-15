package pe.edu.ulima.pm.pokeapp.model

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.firestore.DocumentReference
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
        var pokehash = "/pokemon/"
        dbFirebase.collection("pokemon")
            .whereEqualTo("pokeApiId", idPokemon)
            .get()
            .addOnSuccessListener{ document ->
                if(!document.isEmpty){
                    for(doc in document){
                        pokehash = pokehash + doc.id
                    }
                    dbFirebase.collection("favorites")
                        .whereEqualTo("pokeApiId",idPokemon)
                        .get()
                        .addOnSuccessListener { document ->
                            if(document.isEmpty){
                                val data = hashMapOf<String, Any>(
                                    "pokeApiId" to idPokemon,
                                    "pokemon" to dbFirebase.document(pokehash)
                                    )
                                dbFirebase.collection("favorites").add(data)
                                    .addOnSuccessListener {
                                        Log.i("Add Favorite Pokemon", "Document created successfully")
                                    }
                            }
                        }
                }
            }
    }

    fun deletePkFav(idPokemon:Long) {
        dbFirebase.collection("favorites")
            .whereEqualTo("pokeApiId",idPokemon)
            .get()
            .addOnSuccessListener{ documents ->
                if(!documents.isEmpty){
                   val hash = documents.documents[0].id
                    dbFirebase.collection("favorites").document(hash)
                        .delete()
                        .addOnSuccessListener{
                            Log.i("DeletePkFav","DocumentSnapshot succesfully delete")
                        }
                        .addOnFailureListener{
                            println(it.message!!)
                        }
                }
            }
    }

    fun isPkFav(idPokemon:Long, callbackOK: (Boolean)-> Unit){
        var resp = true
        dbFirebase.collection("favorites")
            .whereEqualTo("pokeApiId",idPokemon)
            .get()
            .addOnSuccessListener{ documents ->
                if(!documents.isEmpty){
                    resp = false
                }
                callbackOK(resp)
            }
            .addOnFailureListener{
                println(it.message!!)
            }
    }

    fun getPokemonsFav(callbackOK: (List<Pokemon>) -> Unit, callbackERROR: (String) -> Unit){
        dbFirebase.collection("favorites")
            .orderBy("pokeApiId")
            .get()
            .addOnSuccessListener{ res ->
                val pokemonsFavList = arrayListOf<Pokemon>()
                val total = res.size()
                var cont = 1
                for(document in res){
                    (document.data["pokemon"]!! as DocumentReference).get().addOnSuccessListener { doc ->
                        val pokemon = Pokemon(
                            doc.data?.get("pokeApiId").toString().toLong(),
                            doc.data?.get("name").toString(),
                            doc.data?.get("hp").toString().toInt(),
                            doc.data?.get("attack").toString().toInt(),
                            doc.data?.get("defense").toString().toInt(),
                            doc.data?.get("spAttack").toString().toInt(),
                            doc.data?.get("spDefense").toString().toInt(),
                            doc.data?.get("imgUrl").toString(),
                            doc.data?.get("isFav").toString().toBoolean()
                        )
                        pokemonsFavList.add(pokemon)
                        if(cont==total){
                            Log.d("PokemonManager","Load favorite pokemons from Firebase")
                            callbackOK(pokemonsFavList)
                        }else{
                            cont++
                        }
                    }
                }
            }
            .addOnFailureListener{
                callbackERROR(it.message!!)
            }
    }
}