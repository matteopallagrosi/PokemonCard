package it.fasm.pokemoncard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat.canScrollHorizontally
import androidx.core.view.iterator
import androidx.recyclerview.widget.LinearLayoutManager
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.adapters.SeriesAdapter
import it.fasm.pokemoncard.databinding.FragmentCardsBinding
import it.fasm.pokemoncard.dbManager.CardDb
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardsFragment : Fragment() {

    private var _binding: FragmentCardsBinding? = null

    private val binding get() = _binding!!

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var adapter = SeriesAdapter(requireContext())
        binding.rvSeries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSeries.adapter = adapter

    }

 */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCardsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.scrollCards.isHorizontalScrollBarEnabled = false

        binding.imageView.setOnClickListener(){
            binding.ivcardforeground.setImageResource(R.drawable.pokemon1)
            binding.ivcardforeground.visibility = View.VISIBLE
            binding.floatingActionButton3.visibility = View.VISIBLE
            var db = CardDbDatabase.getDatabase(requireContext())
            var card1 = CardDb("1", 23)
            CoroutineScope(Dispatchers.IO).launch {
                db.cardDbDao().addCardDb(card1)
            }
        }




        binding.floatingActionButton3.setOnClickListener(){
            binding.floatingActionButton3.visibility  =View.INVISIBLE
            binding.ivcardforeground.visibility = View.INVISIBLE
        }

        val adapter = SeriesAdapter(requireContext())
        binding.rvSeries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSeries.adapter = adapter

        return view


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}