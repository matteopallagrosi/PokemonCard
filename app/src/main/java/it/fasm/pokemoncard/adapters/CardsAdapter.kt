package it.fasm.pokemoncard.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.CardLargeActivity
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.CardLayoutBinding
import it.fasm.pokemoncard.dbManager.CardDbViewModel
import it.fasm.pokemoncard.model.Card

class CardsAdapter(val cards: ArrayList<Card>, val images: HashMap<String, Bitmap>, val context: Context): RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: CardLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        var card: Card = Card()
            set(value) {
                field = value
            }
        var cardLayout = binding.root

        var cardImage = binding.ivCard
        var star = binding.ivstar

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.card = cards[position]
        holder.cardImage.setImageBitmap(images[holder.card.id])
        if (holder.card.favorites) holder.star.setImageResource(R.drawable.star_on)
        else holder.star.setImageResource(R.drawable.star_off)
        holder.cardLayout.setOnClickListener {
            val i = Intent(context, CardLargeActivity::class.java)
            i.putExtra("card", holder.card.id)
            context.startActivity(i)
        }
        holder.star.setOnClickListener(){
            println(holder.card.name + "aggiunta ai preferiti")
            holder.card.favorites = true
            holder.star.setImageResource(R.drawable.star_on)
        }

        /*
        holder.cardLayout.setOnClickListener {
            println(holder.card.name)
        }
        */

    }

    override fun getItemCount(): Int {
        return images.size
    }

    private fun insertDataToDatabase(){

    }
}