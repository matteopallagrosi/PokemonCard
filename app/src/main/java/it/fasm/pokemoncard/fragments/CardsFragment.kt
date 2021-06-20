package it.fasm.pokemoncard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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