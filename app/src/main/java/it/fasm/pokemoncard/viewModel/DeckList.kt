package it.fasm.pokemoncard.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.fasm.pokemoncard.dbManager.DeckDb

class DeckList: ViewModel() {

    private var list = MutableLiveData<MutableList<DeckDb>>()


    fun addDeck(deck: DeckDb) {
        var currentList = mutableListOf<DeckDb>()
        list.value?.let { currentList.addAll(it) }
        currentList.add(deck)
        list.value = currentList //triggera gli observers
    }

    fun getList(): MutableLiveData<MutableList<DeckDb>> {
        return list
    }




}