package it.fasm.pokemoncard.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.CardActivity
import it.fasm.pokemoncard.databinding.SeriesLayoutBinding
import it.fasm.pokemoncard.model.CardSet

class SetsAdapter(val sets: ArrayList<CardSet>, val logos: HashMap<String,Bitmap>, val context: Context): RecyclerView.Adapter<SetsAdapter.ViewHolder>() {


    inner class ViewHolder(binding:SeriesLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        var set: CardSet = CardSet()
        set(value) {
            field = value
        }
        var card = binding.root

        var logoSet = binding.ivSerie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SeriesLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.set = sets[position]
        holder.logoSet.setImageBitmap(logos[holder.set.id])
        holder.card.setOnClickListener {
            val i = Intent(context, CardActivity::class.java)
            i.putExtra("set", holder.set.id)
            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
        return logos.size
    }

}