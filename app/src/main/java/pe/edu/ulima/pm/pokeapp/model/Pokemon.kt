package pe.edu.ulima.pm.pokeapp.model

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Pokemon (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "hp")
    var hp: Int,
    @ColumnInfo(name = "attack")
    var attack: Int,
    @ColumnInfo(name = "defense")
    var defense: Int,
    @ColumnInfo(name = "spAttack")
    var spAttack: Int,
    @ColumnInfo(name = "spDefense")
    var spDefense: Int,
    @ColumnInfo(name = "imgUrl")
    var imgUrl: String,
    @ColumnInfo(name = "isFav")
    var isFav: Boolean
): Serializable{}