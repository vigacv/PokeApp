package pe.edu.ulima.pm.pokeapp.rom

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.ulima.pm.pokeapp.model.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class PokeAppDatabase: RoomDatabase() {
    abstract fun pokemonDAO(): PokemonDAO
}