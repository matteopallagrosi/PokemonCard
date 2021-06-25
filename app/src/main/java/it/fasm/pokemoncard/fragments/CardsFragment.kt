package it.fasm.pokemoncard.fragments

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class CardsFragment : Fragment() {

    /*private val centerImage = intArrayOf(R.drawable.pokemon_1, R.drawable.pokemon_2,R.drawable.pokemon_3,
            R.drawable.pokemon_4,R.drawable.pokemon_5,
            R.drawable.pokemon_6,R.drawable.pokemon_7,R.drawable.pokemon_8,R.drawable.pokemon_9)*/

    private var _binding: FragmentCardsBinding? = null
    private var cards = listOf<CardDb>()
    private val binding get() = _binding!!



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        _binding = FragmentCardsBinding.inflate(inflater, container, false)
        val view = binding.root

        //binding.scrollCards.isHorizontalScrollBarEnabled = false
        //var state = false

        /*
        binding.rvSeries.addOnItemTouchListener(object : SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
                return state
            }
        })

        var i = 1
        binding.layout.forEach {
            val id = resources.getIdentifier("pokemon$i", "drawable", requireContext().packageName)

            it.setOnClickListener(){
                binding.ivcardforeground.setImageResource(id)
                binding.ivcardforeground.visibility = View.VISIBLE
                binding.floatingActionButton3.visibility = View.VISIBLE
                state = true
            }
            i ++
        }*/


        var job = CoroutineScope(Dispatchers.IO).launch {
            val cardDao = CardDbDatabase.getDatabase(requireContext()).getCardDbDao()
            cards = cardDao.getCards()
            println("dsfhsdhfkjsdhgfufufuyfyufuyfyufyufyu")
        }
        runBlocking{
            job.join()
        }

        val centerAdapter = CenterZoomAdapter(requireContext(), binding, cards)
        binding.CenterZoom.layoutManager = CenterZoomLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.CenterZoom.adapter = centerAdapter

        /*binding.floatingActionButton3.setOnClickListener(){
            binding.floatingActionButton3.visibility  =View.INVISIBLE
            binding.ivcardforeground.visibility = View.INVISIBLE
            binding.rvSeries.suppressLayout(false)
            state = false
        }*/

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