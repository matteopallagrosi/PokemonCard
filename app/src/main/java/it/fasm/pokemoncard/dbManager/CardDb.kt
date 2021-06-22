package it.fasm.pokemoncard.dbManager

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "card")
data class CardDb (
    @PrimaryKey
    val id: String = "",
    val name: String,
    val hp: Int? = null,
    val shop: String?,
    val pokedexNumber: Int?,
    val rarity: String?,
    val image: Bitmap?,
    val deck: String = ""

)
