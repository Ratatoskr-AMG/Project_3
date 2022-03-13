package ru.ratatoskr.project_3.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.ratatoskr.project_3.data.contracts.FavoritesContract
import ru.ratatoskr.project_3.data.contracts.HeroesContract

@Serializable
@Entity(tableName = FavoritesContract.FAVORITES_TABLE_NAME)
data class Favorites(
    @PrimaryKey(autoGenerate = true)
    @Expose
    var id: Int,

    @Expose
    var heroId: Int,

)