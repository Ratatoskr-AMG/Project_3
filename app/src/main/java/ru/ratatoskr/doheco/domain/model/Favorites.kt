package ru.ratatoskr.doheco.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.ratatoskr.doheco.data.contracts.FavoritesContract
import ru.ratatoskr.doheco.data.contracts.HeroesContract

@Entity(tableName = ru.ratatoskr.doheco.data.contracts.FavoritesContract.FAVORITES_TABLE_NAME)
data class Favorites(
    @PrimaryKey (autoGenerate = true)
    var id: Int?,
    var heroId: Int,

)