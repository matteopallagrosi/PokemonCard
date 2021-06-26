package it.fasm.pokemoncard.adapters

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import it.fasm.pokemoncard.fragments.CardLargeFragment
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.CardLayoutBinding
import it.fasm.pokemoncard.dbManager.CardDb
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import it.fasm.pokemoncard.model.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardsAdapter(val cards: ArrayList<Card>, private val images: HashMap<String, Bitmap>, val context: Context, private var deckList: List<String>): RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    private var listener: OnStarClickListener? = null

    inner class ViewHolder(binding: CardLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        var card: Card = Card()
        var cardLayout = binding.root

        var cardImage = binding.ivCardPref
        var star = binding.ivstar


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardLayoutBinding.inflate(layoutInflater, parent, false)
        val holder = ViewHolder(binding)

        holder.cardLayout.setOnClickListener {
            println("Hai cliccato!")
            val activity = it.context as AppCompatActivity
            val cardLargeFragment = CardLargeFragment()
            val bundle = Bundle()
            bundle.putSerializable("card", holder.card)
            cardLargeFragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentHost, cardLargeFragment)
                    .addToBackStack(null).commit()
        }

        holder.star.setOnClickListener {
            if (!holder.card.favorites){
                MaterialDialog(context).show {
                    listItems(items = deckList) { _, _, text ->
                        if (text.toString() == "You must create a deck first!") this.cancel()
                        else {
                            holder.card.favorites = true
                            holder.star.setImageResource(R.drawable.star_on)
                            insertDataToDatabase(holder.card, images[holder.card.id], text.toString())
                            println(holder.card.name + "aggiunta ai preferiti")
                            //incrementare numero stelle
                            listener?.onStarAdded()
                        }
                    }
                }

            } else {
                holder.card.favorites = false
                holder.star.setImageResource(R.drawable.star_off)
                deleteDataFromDatabase(holder.card.id)
                println(holder.card.name + "rimossa dai preferiti")
                listener?.onStarRemoved()
            }
        }

        return holder
    }

    interface OnStarClickListener {
        fun onStarAdded()

        fun onStarRemoved()
    }

    fun setWhenClickListener(listener: OnStarClickListener) {
        this.listener = listener
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.card = cards[position]

        holder.cardImage.setImageBitmap(images[holder.card.id])


        holder.star.isEnabled = false

        if (holder.card.downloaded) {
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


    }

    override fun getItemCount(): Int {
        return images.size
    }

    private fun insertDataToDatabase(card: Card, image: Bitmap?, deck: String){
        val cardDb : CardDb
        var price: Float? = 0.0f
        if (card.tcgplayer.prices.normal.mid != null) {
            price = card.tcgplayer.prices.normal.mid
        }
        else if (card.tcgplayer.prices.holofoil.mid != null){
            price = card.tcgplayer.prices.holofoil.mid
        }
        else if (card.tcgplayer.prices.reverseHolofoil.mid != null){
            price = card.tcgplayer.prices.reverseHolofoil.mid
        }



        cardDb = CardDb(card.id, card.name, card.hp, card.tcgplayer.url, card.nationalPokedexNumbers?.get(0), card.rarity, card.set.id, image, deck, price)


        val cardDao = CardDbDatabase.getDatabase(this.context).getCardDbDao()

        CoroutineScope(Dispatchers.IO).launch {
            cardDao.addCard(cardDb)
        }

    }

    private fun deleteDataFromDatabase(id: String){
        val cardDao = CardDbDatabase.getDatabase(this.context).getCardDbDao()

        CoroutineScope(Dispatchers.IO).launch {
            cardDao.deleteCard(id)
        }
    }
}