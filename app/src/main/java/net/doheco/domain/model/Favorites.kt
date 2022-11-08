package net.doheco.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = net.doheco.data.contracts.FavoritesContract.FAVORITES_TABLE_NAME)
data class Favorites(
    @PrimaryKey (autoGenerate = true)
    var id: Int?,
    var heroId: Int,

)