package it.fasm.pokemoncard.dbManager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDbDao {

    @Insert
    fun addCard(card: CardDb)


}