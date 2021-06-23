package it.fasm.pokemoncard.fragments

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.android.material.dialog.MaterialDialogs
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.DeckBinding
import it.fasm.pokemoncard.databinding.FragmentFavoritesBinding
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import it.fasm.pokemoncard.dbManager.DeckDb
import kotlinx.coroutines.*


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root
        val cancelText = "<font color='#000000'>Cancel</font>"
        var result: Long = 0
        var job: Job = Job()
        var deckName: String = ""
        binding.btnAdd.setOnClickListener(){
            var dialog = MaterialDialog(requireContext()).show {
                input { dialog, text ->
                    job = CoroutineScope(Dispatchers.IO).launch {
                        val cardDao = CardDbDatabase.getDatabase(requireContext()).getCardDbDao()
                        result = cardDao.addDeck(DeckDb(text.toString()))
                        deckName = text.toString()
                    }

                    runBlocking {
                        job.join()
                    }
                    if (result == -1L)
                        Toast.makeText(requireContext(), "Name already exists", Toast.LENGTH_LONG).show()
                    else
                        addItem(deckName)
                }
                positiveButton(R.string.submit)
                title(R.string.deckname)
                negativeButton(text = Html.fromHtml(cancelText))
            }

        }

        return view
    }

    private fun addItem(deckName: String){

        val newView: LinearLayout = this.layoutInflater.inflate(R.layout.deck, null) as LinearLayout
        newView.children.forEach {
            if (it is CardView)
                it.children.forEach {
                    if (it.id == R.id.textView5 && (it is TextView)) {
                        it.text = deckName
                    }
                }
        }

        binding.layout.addView(newView,0)
        println(newView.id)
        binding.layout.id
    }


}