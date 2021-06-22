package it.fasm.pokemoncard.fragments

import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.adapters.CardsAdapter
import it.fasm.pokemoncard.databinding.FragmentCardListBinding

import it.fasm.pokemoncard.model.Card

class CardListFragment : Fragment() {

    private lateinit var binding: FragmentCardListBinding
    private lateinit var adapter: CardsAdapter
    private var cards = ArrayList<Card>()
    private var cardImages = HashMap<String , Bitmap>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCardListBinding.inflate(layoutInflater, container, false)
        return binding.root

        val set = intent.extras?.getString("set")

        println(set)

        class GridSpacingItemDecoration(
            private val spanCount: Int,
            private val spacing: Int,
            private val includeEdge: Boolean
        ) :
            RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {

                val position = parent.getChildAdapterPosition(view) // item position
                val column = position % spanCount // item column
                if (includeEdge) {
                    outRect.left =
                        spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right =
                        (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                    if (position < spanCount) { // top edge
                        outRect.top = spacing
                    }
                    outRect.bottom = spacing // item bottom
                } else {
                    outRect.left =
                        column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                    outRect.right =
                        spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                    if (position >= spanCount) {
                        outRect.top = spacing // item top
                    }
                }
            }

        }


        binding.rvCards.layoutManager = GridLayoutManager(this, 3)
        adapter = CardsAdapter(cards, cardImages, this)
        binding.rvCards.adapter = adapter

        val spanCount = 3 // 3 columns

        val spacing = 20 // 50px

        val includeEdge = true
        binding.rvCards.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))

        setUICard(set)
    }



}