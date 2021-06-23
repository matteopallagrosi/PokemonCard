package it.fasm.pokemoncard.dbManager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeckDb(
    @PrimaryKey
    var name: String = ""
)