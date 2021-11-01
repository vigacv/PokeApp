package pe.edu.ulima.pm.pokeapp.model

import android.content.Context
import androidx.room.Room
import pe.edu.ulima.pm.pokeapp.rom.PokeAppDatabase
import pe.edu.ulima.pm.pokeapp.services.PokeAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


class PokemonManager(private val context: Context) {

    private val db = Room.databaseBuilder(context, PokeAppDatabase
    ::class.java, "db_videogames").allowMainThreadQueries().build()

    val API_BASE_URL = "https://pokeapi.co/api/v2/"

    fun getPokemons(page: Int, callbackOK: (List<Pokemon>) -> Unit, callbackError: (String) -> Unit){
        try {
            val pokemonsRoom = getPokemonsRoom(page)
            if(pokemonsRoom.isNotEmpty()){
                println("Loading pokemons from Room...")
                println(pokemonsRoom)
                callbackOK(pokemonsRoom)
            }else{
                println("Loading pokemons from API...")
                getPokemonsRetrofit(page, callbackOK, callbackError)
            }
        }catch (exception: Exception){
            exception.message?.let { callbackError(it) }
        }
    }

    fun getPokemonsRetrofit(page: Int, callbackOK: (List<Pokemon>) -> Unit, callbackError: (String) -> Unit){
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PokeAPIService::class.java)

        service.getAllPokemon(page*20).enqueue(object: Callback<PokeApiResult>{
            override fun onResponse(p0: Call<PokeApiResult>, p1: Response<PokeApiResult>) {
                if(context != null){
                    var pokemonList = mutableListOf<Pokemon>()
                    val results = p1.body()?.results
                    results!!.forEach { pokemon: PokemonItem ->
                        val pokemonName = pokemon.name
                        service.getPokemonStats(pokemon.name).enqueue(object: Callback<PokemonApiInfo>{
                            override fun onResponse(
                                p0: Call<PokemonApiInfo>,
                                p1: Response<PokemonApiInfo>
                            ) {
                                val pokemonHp = p1.body()?.stats!![0].base_stat
                                val pokemonAttack = p1.body()?.stats!![1].base_stat
                                val pokemonDefense = p1.body()?.stats!![2].base_stat
                                val pokemonSpAttack = p1.body()?.stats!![3].base_stat
                                val pokemonSpDefense = p1.body()?.stats!![4].base_stat
                                val pokemonImgUrl = p1.body()?.sprites?.front_default

                                val newPokemon = Pokemon(0,pokemonName, pokemonHp, pokemonAttack, pokemonDefense, pokemonSpAttack, pokemonSpDefense, pokemonImgUrl!!)
                                pokemonList.add(newPokemon)

                                if(pokemonList.size >= results.size){
                                    if(context != null) saveIntoRoom(pokemonList)
                                    callbackOK(pokemonList)
                                }
                            }

                            override fun onFailure(p0: Call<PokemonApiInfo>, p1: Throwable) {
                                p1.message?.let { callbackError(it) }
                            }
                        })
                    }
                }
            }

            override fun onFailure(p0: Call<PokeApiResult>, p1: Throwable) {
                p1.message?.let { callbackError(it) }
            }
        })
    }

    fun getPokemonsRoom(page: Int): List<Pokemon>{
        return db.pokemonDAO().findAll((page*20))
    }

    private fun saveIntoRoom(pokemons: List<Pokemon>){
        pokemons.forEach {
            db.pokemonDAO().insert(it)
        }
    }
}