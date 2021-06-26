package it.fasm.pokemoncard.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.fragments.CardListFragment
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.SeriesLayoutBinding
import it.fasm.pokemoncard.model.CardSet

class SetsAdapter(val sets: ArrayList<CardSet>, private val logos: HashMap<String,Bitmap>, val context: Context): RecyclerView.Adapter<SetsAdapter.ViewHolder>() {


    inner class ViewHolder(binding:SeriesLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        var set: CardSet = CardSet()
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
            val activity = it.context as AppCompatActivity
            val cardListFragment = CardListFragment()
            val bundle = bundleOf("set" to holder.set.id)
            cardListFragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentHost, cardListFragment)
                .addToBackStack(null).commit()
        }

    }

    override fun getItemCount(): Int {
        return logos.size
    }

}