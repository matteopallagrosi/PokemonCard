package it.fasm.pokemoncard.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.CardActivity
import it.fasm.pokemoncard.databinding.CardLayoutBinding
import it.fasm.pokemoncard.model.Card
import it.fasm.pokemoncard.model.CardSet

class CardsAdapter(val cards: ArrayList<Card>, val images: HashMap<String, Bitmap>, val context: Context): RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: CardLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        var card: Card = Card()
            set(value) {
                field = value
            }
        var cardLayout = binding.root

        var cardImage = binding.ivCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.card = cards[position]
        holder.cardImage.setImageBitmap(images[holder.card.id])
        /* holder.cardLayout.setOnClickListener {
            val i = Intent(context, CardActivity::class.java)
            i.putExtra("set", holder.set.id)
            context.startActivity(i)
        } */
        holder.cardLayout.setOnClickListener {
            println(holder.card.name)
        }


    }

    override fun getItemCount(): Int {
        return images.size
    }
}