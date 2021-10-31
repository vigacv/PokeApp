package pe.edu.ulima.pm.pokeapp.model

import android.content.Context
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.google.gson.JsonObject
import pe.edu.ulima.pm.pokeapp.services.PokeAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PokemonManager(private val context: Context) {

    val API_BASE_URL = "https://pokeapi.co/api/v2/"

    fun getPokemonsRetrofit(callbackOK: (List<Pokemon>) -> Unit, callbackError: (String) -> Unit){
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PokeAPIService::class.java)

        service.getAllPokemon().enqueue(object: Callback<PokeApiResult>{
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

                                val newPokemon = Pokemon(pokemonName, pokemonHp, pokemonAttack, pokemonDefense, pokemonSpAttack, pokemonSpDefense, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")
                                println("New pokemon: $newPokemon")
                                pokemonList.add(newPokemon)

                                if(pokemonList.size >= results.size){
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
}