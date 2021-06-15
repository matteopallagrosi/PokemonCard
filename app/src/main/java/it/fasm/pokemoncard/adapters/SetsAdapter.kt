package it.fasm.pokemoncard.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.databinding.CardLayoutBinding
import it.fasm.pokemoncard.model.CardSet

class SetsAdapter(val sets: List<CardSet>, val logos: ArrayList<Bitmap>): RecyclerView.Adapter<SetsAdapter.ViewHolder>() {


    inner class ViewHolder(binding:CardLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        var set: CardSet = CardSet()
        set(value) {
            field = value
        }

        var logoSet = binding.ivSerie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.set = sets[position]
        holder.logoSet.setImageBitmap(logos[position])

    }

    override fun getItemCount(): Int {
        return sets.size
    }

}