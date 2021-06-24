package it.fasm.pokemoncard.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.SetsFragment
import it.fasm.pokemoncard.animation.CenterZoomLayoutManager
import it.fasm.pokemoncard.databinding.CenterLayoutBinding
import it.fasm.pokemoncard.databinding.FragmentCardsBinding
import it.fasm.pokemoncard.databinding.SeriesLayoutBinding

class CenterZoomAdapter(val context: Context): RecyclerView.Adapter<CenterZoomAdapter.ViewHolder>() {

    private val centerImage = intArrayOf(R.drawable.pokemon_1, R.drawable.pokemon_2,R.drawable.pokemon_3,
            R.drawable.pokemon_4,R.drawable.pokemon_5,
            R.drawable.pokemon_6,R.drawable.pokemon_7,R.drawable.pokemon_8,R.drawable.pokemon_9)

    inner class ViewHolder(binding: CenterLayoutBinding ): RecyclerView.ViewHolder(binding.root) {

        var card = binding.root
        var ivcenter = binding.ivCenter
        var imglarge = binding.imgLarge

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CenterLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivcenter.setImageResource(centerImage[position])


            holder.ivcenter.setOnClickListener() {
                //holder.imglarge.setImageResource(R.drawable.pokemon1)
                //holder.ivcenter.visibility = View.GONE
                //holder.imglarge.visibility = View.VISIBLE
                //binding2.floatingActionButton3.visibility = View.VISIBLE
            }
    }



    override fun getItemCount(): Int {
       return centerImage.size
    }

}

