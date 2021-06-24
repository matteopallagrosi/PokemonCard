package it.fasm.pokemoncard.fragments

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.FragmentCardsBinding
import it.fasm.pokemoncard.databinding.FragmentCardsDeckBinding
import it.fasm.pokemoncard.dbManager.CardDb
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CardsDeckFragment : Fragment() {

    private lateinit var binding: FragmentCardsDeckBinding
    private var cardList = listOf<CardDb>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deck = arguments?.getString("deck")
        var job = CoroutineScope(Dispatchers.IO).launch {
            val cardDao = CardDbDatabase.getDatabase(requireContext()).getCardDbDao()
            if (deck != null) {
                cardList = cardDao.getCardsDeck(deck)
            }
        }
        runBlocking {
            job.join()
        }
    }

    private fun addItem(cardDb: CardDb){

        val newView: ConstraintLayout = this.layoutInflater.inflate(R.layout.card, null) as ConstraintLayout
        var parem: GridLayout.LayoutParams = GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f))
        newView.layoutParams = parem
        newView.children.forEach {
            if (it.id == R.id.ivCard && it is ImageView) {
                it.maxHeight = 80
                it.maxWidth = 80
            }
        }

        newView.children.forEach {
            if (it.id == R.id.ivCard && it is ImageView)  it.setImageBitmap(cardDb.image)
        }

        binding.layout.addView(newView, 0)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardsDeckBinding.inflate(inflater, container, false)

        for (card in cardList) {
            addItem(card)
        }
        return binding.root
    }

}