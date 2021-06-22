package it.fasm.pokemoncard.dbManager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import it.fasm.pokemoncard.model.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardDbViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<CardDb>>
    private val repository: CardDbRepository

    init {
        val cardDbDao = CardDbDatabase.getDatabase(application).cardDbDao()
        repository = CardDbRepository(cardDbDao)
        readAllData = repository.readAllData
    }

    fun addCardDb(cardDb: CardDb){
        viewModelScope.launch(Dispatchers.IO){
            repository.addCardDb(cardDb)
        }
    }
}