package it.fasm.pokemoncard.dbManager

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "card_table")
data class CardDb (@PrimaryKey
    var id: String = "",
    var hp: Int? = null
)