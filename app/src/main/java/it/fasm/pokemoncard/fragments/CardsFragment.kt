package it.fasm.pokemoncard.fragments

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.adapters.CenterZoomAdapter
import it.fasm.pokemoncard.adapters.SeriesAdapter
import it.fasm.pokemoncard.animation.CenterZoomLayoutManager
import it.fasm.pokemoncard.databinding.FragmentCardsBinding
import it.fasm.pokemoncard.dbManager.CardDb
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import kotlinx.coroutines.*


class CardsFragment : Fragment() {


    private var _binding: FragmentCardsBinding? = null
    private var cards = listOf<CardDb>()
    private val binding get() = _binding!!
    private lateinit var centerAdapter: CenterZoomAdapter
    private lateinit var cont: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        cont = requireContext()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        _binding = FragmentCardsBinding.inflate(inflater, container, false)
        val view = binding.root


        centerAdapter = CenterZoomAdapter(cont, binding, cards)
        binding.CenterZoom.layoutManager = CenterZoomLayoutManager(cont, LinearLayoutManager.HORIZONTAL, false)
        binding.CenterZoom.adapter = centerAdapter

        val adapter = SeriesAdapter(cont)
        adapter.setListener(activity as SeriesAdapter.OnSerieClickListener)
        binding.rvSeries.layoutManager = LinearLayoutManager(cont)
        binding.rvSeries.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            val cardDao = CardDbDatabase.getDatabase(cont).getCardDbDao()
            cards = cardDao.getCards()
            launch(Dispatchers.Main) {
                updateUI()
            }
        }
    }

    private fun updateUI() {
        println(cards.size)
        cards = cards.takeLast(10)
        centerAdapter = CenterZoomAdapter(cont, binding, cards)
        binding.CenterZoom.adapter = centerAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}