package it.fasm.pokemoncard.dbManager

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "card")
data class CardDb (
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val hp: Int? = null,
    val shop: String? = "",
    val pokedexNumber: Int? = 0,
    val rarity: String? = "",
    val idSet: String? = "",
    val image: Bitmap? = null,
    val deck: String = "",
    val price: Float? = null
): java.io.Serializable
