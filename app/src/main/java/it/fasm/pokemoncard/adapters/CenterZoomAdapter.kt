package it.fasm.pokemoncard.adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.CenterLayoutBinding
import it.fasm.pokemoncard.databinding.FragmentCardsBinding
import it.fasm.pokemoncard.dbManager.CardDb



class CenterZoomAdapter(val context: Context, private var fragmentBinding: FragmentCardsBinding, var cards : List<CardDb> ): RecyclerView.Adapter<CenterZoomAdapter.ViewHolder>() {



    private val centerImage = intArrayOf(R.drawable.pokemon_1, R.drawable.pokemon_2,R.drawable.pokemon_3,
            R.drawable.pokemon_4,R.drawable.pokemon_5,
            R.drawable.pokemon_6,R.drawable.pokemon_7,R.drawable.pokemon_8,R.drawable.pokemon_9)

    inner class ViewHolder(binding: CenterLayoutBinding ): RecyclerView.ViewHolder(binding.root) {

        var card = binding.root
        var ivcenter = binding.ivCenter


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CenterLayoutBinding.inflate(layoutInflater, parent, false)



        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (cards.isNotEmpty()){

            holder.ivcenter.setImageBitmap(cards[position].image)
        }else{
            holder.ivcenter.setImageResource(centerImage[position])
        }


        val state = false
        fragmentBinding.rvSeries.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
                return state
            }
        })


        val pos = position +1
        val id = context.resources.getIdentifier("pokemon$pos", "drawable", context.packageName)

        holder.ivcenter.setOnClickListener {
            if (cards.isEmpty()){
                fragmentBinding.ivcardforeground.setImageResource(id)}
            else{
                fragmentBinding.ivcardforeground.setImageBitmap(cards[position].image)
            }

            fragmentBinding.ivcardforeground.visibility = View.VISIBLE
        }

        fragmentBinding.ivcardforeground.setOnClickListener {
            fragmentBinding.floatingActionButton3.visibility  =View.INVISIBLE
            fragmentBinding.ivcardforeground.visibility = View.INVISIBLE
        }

    }



    override fun getItemCount(): Int {
       if(cards.isEmpty()){
        return centerImage.size}
        return cards.size
    }

}

