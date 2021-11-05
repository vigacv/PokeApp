package pe.edu.ulima.pm.pokeapp.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

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
    val sprites: Sprites,
    val stats: List<PokemonStat>
)

data class PokemonStat(
    val base_stat: Int,
    val effort: Int
)

data class Sprites(
    val front_default: String,
    val other: Other
)
data class Other(
    @SerializedName("official-artwork")
    val officialartwork: OfficialArtwork
)
data class OfficialArtwork(
    val front_default: String
)