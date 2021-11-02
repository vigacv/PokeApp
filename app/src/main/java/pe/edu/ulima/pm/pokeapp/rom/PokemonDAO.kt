package pe.edu.ulima.pm.pokeapp.rom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pe.edu.ulima.pm.pokeapp.model.Pokemon

@Dao
interface PokemonDAO {
    @Query("SELECT * FROM POKEMON LIMIT 20 OFFSET :pageIndex")
    fun findAll(pageIndex: Int): List<Pokemon>

    @Insert
    fun insert(pokemon: Pokemon)

    @Query("SELECT * FROM POKEMON WHERE isFav = 1 ")
    fun findFavorites(): List<Pokemon>

    @Query("UPDATE POKEMON SET isFav = :flag WHERE id = :id ")
    fun updateFav(id:Long, flag:Boolean)

    @Query("DELETE FROM POKEMON WHERE id = :id")
    fun deleteById(id:Long)

    @Query("SELECT isFav FROM POKEMON WHERE id = :id")
    fun isPkFav(id:Long): Boolean
}