package it.fasm.pokemoncard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat.canScrollHorizontally
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.iterator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.adapters.SeriesAdapter
import it.fasm.pokemoncard.databinding.FragmentCardsBinding

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
        var state = false
        binding.rvSeries.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
                return state
            }
        })

        var i = 1
        binding.layout.children.forEach {
            val id = resources.getIdentifier("pokemon$i", "drawable", context?.packageName)
            it.setOnClickListener(){
                //var d = ContextCompat.getDrawable(requireContext(),id)
                binding.ivcardforeground.setImageResource(id)
                binding.ivcardforeground.visibility = View.VISIBLE
                binding.floatingActionButton3.visibility = View.VISIBLE
                state = true
            }
            i++
        }

        binding.floatingActionButton3.setOnClickListener(){
            binding.floatingActionButton3.visibility  =View.INVISIBLE
            binding.ivcardforeground.visibility = View.INVISIBLE
            state = false
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