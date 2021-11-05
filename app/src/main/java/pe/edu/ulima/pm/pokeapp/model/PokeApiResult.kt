package pe.edu.ulima.pm.pokeapp.model

import com.google.gson.JsonObject

data class PokeApiResult (
    var count: Int?,
    var next: String?,
    var previous: String?,
    var results: List<PokemonItem>
)

data class PokemonItem(
    val name: String,
    val url: String
)

data class PokemonApiInfo(
    val id: Int,
    val stats: List<PokemonStat>
)

data class PokemonStat(
    val base_stat: Int,
    val effort: Int
)
