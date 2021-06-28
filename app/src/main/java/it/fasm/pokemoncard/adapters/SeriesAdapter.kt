package it.fasm.pokemoncard.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.fragments.SetsFragment
import it.fasm.pokemoncard.databinding.SeriesLayoutBinding

class SeriesAdapter(val context: Context): RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    private lateinit var listener: OnSerieClickListener

    private val series = intArrayOf(R.drawable.sun_moon, R.drawable.black_white,
            R.drawable.hearthgold, R.drawable.sword_shield, R.drawable.xy)

    inner class ViewHolder(binding: SeriesLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        var card = binding.root
        var ivSerie = binding.ivSerie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SeriesLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivSerie.setImageResource(series[position])
        holder.card.setOnClickListener {
            val serie = when (series[position]) {
                R.drawable.sun_moon -> "Sun&Moon"
                R.drawable.black_white -> "Black&White"
                R.drawable.hearthgold -> "HeartGold&SoulSilver"
                R.drawable.sword_shield -> "Sword&Shield"
                R.drawable.xy -> "XY"
                else -> "Sun&Moon"
            }
            //var activity = it.context as AppCompatActivity
            //var setsFragment = SetsFragment()
            //val bundle = bundleOf("serie" to serie)
            //setsFragment.arguments = bundle
            //activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentHost, setsFragment)
            //   .addToBackStack(null).commit();
            listener.onSerieClick(serie)
        }

    }

    override fun getItemCount(): Int {
        return series.size
    }

    fun setListener(listener: OnSerieClickListener) {
        this.listener = listener
    }

    interface OnSerieClickListener {
        fun onSerieClick(serie: String)
    }
}