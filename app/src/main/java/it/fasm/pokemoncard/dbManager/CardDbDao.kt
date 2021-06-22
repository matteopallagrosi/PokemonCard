package it.fasm.pokemoncard.dbManager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDbDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCardDb(cardDb: CardDb)
    @Insert
    fun insertAll(cardDb: CardDb)

    @Query ("SELECT * FROM card_table")
    fun readAllData(): LiveData<List<CardDb>>

}