package it.fasm.pokemoncard.adapters

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import it.fasm.pokemoncard.CardLargeFragment
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.CardLayoutBinding
import it.fasm.pokemoncard.dbManager.CardDb
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import it.fasm.pokemoncard.model.Card
import it.fasm.pokemoncard.viewModel.DeckList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardsAdapter(val cards: ArrayList<Card>, val images: HashMap<String, Bitmap>, val context: Context, var deckList: List<String>): RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

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

        holder.star.isEnabled = false

        if (holder.card.downloaded == true) {
            holder.star.isEnabled = true
        }

        CoroutineScope(Dispatchers.IO).launch {
            val cardDao = CardDbDatabase.getDatabase(context).getCardDbDao()
            if (cardDao.checkCard(holder.card.id) == 1){
                holder.card.favorites = true
            }
        }


        if (holder.card.favorites) holder.star.setImageResource(R.drawable.star_on)
        else holder.star.setImageResource(R.drawable.star_off)
        holder.cardLayout.setOnClickListener {
            println("Hai cliccato!")
            var activity = it.context as AppCompatActivity
            var cardLargeFragment = CardLargeFragment()
            val bundle = Bundle()
            bundle.putSerializable("card", holder.card)
            cardLargeFragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentHost, cardLargeFragment)
                .addToBackStack(null).commit();
        }
        holder.star.setOnClickListener(){
            if (holder.card.favorites == false){
                 MaterialDialog(context).show {
                    listItems(items = deckList) { dialog, index, text ->
                        holder.card.favorites = true
                        holder.star.setImageResource(R.drawable.star_on)
                        insertDataToDatabase(holder.card, images[holder.card.id], text.toString())
                        println(holder.card.name + "aggiunta ai preferiti")
                    }
                }

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

    private fun insertDataToDatabase(card: Card, image: Bitmap?, deck: String){
        var cardDb = CardDb(card.id, card.name, card.hp, card.tcgplayer.url, card.nationalPokedexNumbers!![0], card.rarity, card.set.id, image, deck)
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