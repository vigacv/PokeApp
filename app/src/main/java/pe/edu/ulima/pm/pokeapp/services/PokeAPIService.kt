package pe.edu.ulima.pm.pokeapp.services

import pe.edu.ulima.pm.pokeapp.model.PokeApiResult
import pe.edu.ulima.pm.pokeapp.model.PokemonApiInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAPIService {

    @GET("pokemon?limit=20")
    fun getAllPokemon(@Query("offset") offset: Int): Call<PokeApiResult>

    @GET("pokemon/{name}")
    fun getPokemonStats(@Path("name") name: String): Call<PokemonApiInfo>
}