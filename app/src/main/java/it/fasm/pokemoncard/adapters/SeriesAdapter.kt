package it.fasm.pokemoncard.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.CardLayoutBinding

class SeriesAdapter: RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    private val series = intArrayOf(R.drawable.sun_moon, R.drawable.black_white,
            R.drawable.hearthgold, R.drawable.sword_shield, R.drawable.xy)

    inner class ViewHolder(binding: CardLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        var ivSerie = binding.ivSerie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = CardLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivSerie.setImageResource(series[position])
    }

    override fun getItemCount(): Int {
        return series.size
    }
}