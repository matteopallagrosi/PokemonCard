package it.fasm.pokemoncard.adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.SetsActivity
import it.fasm.pokemoncard.databinding.ActivityMainBinding
import it.fasm.pokemoncard.databinding.CardLayoutBinding

class SeriesAdapter(val context: Context): RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    private val series = intArrayOf(R.drawable.sun_moon, R.drawable.black_white,
            R.drawable.hearthgold, R.drawable.sword_shield, R.drawable.xy)

    inner class ViewHolder(binding: CardLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        var card = binding.root
        var ivSerie = binding.ivSerie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivSerie.setImageResource(series[position])
        holder.card.setOnClickListener {
            Log.w("prova", "ciao")
            val i = Intent(context, SetsActivity::class.java)
            val serie = when (series[position]) {
                R.drawable.sun_moon -> "Sun&Moon"
                R.drawable.black_white -> "Black&White"
                R.drawable.hearthgold -> "HeartGold&SoulSilver"
                R.drawable.sword_shield -> "Sword&Shield"
                R.drawable.xy -> "XY"
                else -> "Sun&Moon"
            }
            i.putExtra("serie", serie)
            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
        return series.size
    }
}