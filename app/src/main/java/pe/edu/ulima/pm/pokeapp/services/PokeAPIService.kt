package pe.edu.ulima.pm.pokeapp.services

import com.google.gson.JsonObject
import pe.edu.ulima.pm.pokeapp.model.PokeApiResult
import pe.edu.ulima.pm.pokeapp.model.Pokemon
import pe.edu.ulima.pm.pokeapp.model.PokemonApiInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeAPIService {

    @GET("pokemon")
    fun getAllPokemon(): Call<PokeApiResult>

    @GET("pokemon/{name}")
    fun getPokemonStats(@Path("name") name: String): Call<PokemonApiInfo>
}