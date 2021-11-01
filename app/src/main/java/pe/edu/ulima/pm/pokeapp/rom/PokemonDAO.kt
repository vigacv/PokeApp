package pe.edu.ulima.pm.pokeapp.rom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pe.edu.ulima.pm.pokeapp.model.Pokemon

@Dao
interface PokemonDAO {
    @Query("SELECT * FROM POKEMON LIMIT 20 OFFSET :pageIndex")
    fun findAll(pageIndex: Int): List<Pokemon>

    @Insert
    fun insert(pokemon: Pokemon)
}