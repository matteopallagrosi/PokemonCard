package it.fasm.pokemoncard.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.fasm.pokemoncard.CardLargeActivity
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.CardLayoutBinding
import it.fasm.pokemoncard.dbManager.CardDb
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import it.fasm.pokemoncard.model.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        CoroutineScope(Dispatchers.IO).launch {
            val cardDao = CardDbDatabase.getDatabase(context).getCardDbDao()
            if (cardDao.checkCard(holder.card.id) == 1){
                holder.card.favorites = true
            }
        }


        if (holder.card.favorites) holder.star.setImageResource(R.drawable.star_on)
        else holder.star.setImageResource(R.drawable.star_off)
        holder.cardLayout.setOnClickListener {
            val i = Intent(context, CardLargeActivity::class.java)
            i.putExtra("card", holder.card.id)
            context.startActivity(i)
        }
        holder.star.setOnClickListener(){
            if (holder.card.favorites == false){
                holder.card.favorites = true
                holder.star.setImageResource(R.drawable.star_on)
                insertDataToDatabase(holder.card, images[holder.card.id])
                println(holder.card.name + "aggiunta ai preferiti")
            } else {
                holder.card.favorites = false
                holder.star.setImageResource(R.drawable.star_off)
                deleteDataFromDatabase(holder.card.id)
                println(holder.card.name + "rimossa dai preferiti")
            }
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

    private fun insertDataToDatabase(card: Card, image: Bitmap?){
        var cardDb = CardDb(card.id, card.name, card.hp, card.tcgplayer.url, card.nationalPokedexNumbers!![0], card.rarity, card.set.id, image)
        val cardDao = CardDbDatabase.getDatabase(this.context).getCardDbDao()

        CoroutineScope(Dispatchers.IO).launch {
            cardDao.addCard(cardDb)
            println("aggiunto!")
        }

    }

    private fun deleteDataFromDatabase(id: String){
        val cardDao = CardDbDatabase.getDatabase(this.context).getCardDbDao()

        CoroutineScope(Dispatchers.IO).launch {
            cardDao.deleteCard(id)
            println("rimosso!")
        }
    }
}