package it.fasm.pokemoncard.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.DeckBinding
import it.fasm.pokemoncard.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.btnAdd.setOnClickListener(){
            addItem()

        }


        return view
    }

    private fun addItem(){

        val newView: CardView = this.layoutInflater.inflate(R.layout.deck, null) as CardView
        newView.children.forEach {
            if (it.id == R.id.textView5 && (it is TextView))
                { it.text = "ciao" }
        }

        binding.layout.addView(newView,0)
        println(newView.id)
        binding.layout.id
    }


}