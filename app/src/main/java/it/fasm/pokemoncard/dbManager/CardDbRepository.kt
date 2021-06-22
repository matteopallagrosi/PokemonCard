package it.fasm.pokemoncard.dbManager

import androidx.lifecycle.LiveData

class CardDbRepository(private val cardDbDao: CardDbDao) {

    val readAllData: LiveData<List<CardDb>> = cardDbDao.readAllData()


    suspend fun addCardDb(cardDb: CardDb) {
        cardDbDao.addCardDb(cardDb)

    }
}