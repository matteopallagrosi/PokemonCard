package it.fasm.pokemoncard.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.FragmentCardLargeFavoritesBinding
import it.fasm.pokemoncard.databinding.FragmentCardsDeckBinding
import it.fasm.pokemoncard.dbManager.CardDb
import it.fasm.pokemoncard.model.Card

class CardLargeFavoritesFragment : Fragment() {

    private lateinit var binding: FragmentCardLargeFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        var cardDb = arguments?.getSerializable("card") as CardDb
        setDescription(cardDb)
        super.onActivityCreated(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentCardLargeFavoritesBinding.inflate(inflater, container, false)


    return binding.root
    }

    private fun setDescription(cardDb: CardDb) {

        /*
        if (card.tcgplayer.prices.normal.mid != null) {
            binding.tvcardprice.text = card.tcgplayer.prices.normal.mid.toString() + "$"
        }
        else if (card.tcgplayer.prices.holofoil.mid != null){
            binding.tvcardprice.text = card.tcgplayer.prices.holofoil.mid.toString() + "$"
        }
        else if (card.tcgplayer.prices.reverseHolofoil.mid != null){
            binding.tvcardprice.text = card.tcgplayer.prices.reverseHolofoil.mid.toString() + "$"
        }
        else {
            binding.tvcardprice.text = "-"
        }
        */

        binding.ivcardlarge.setImageBitmap(cardDb.image)

        if (cardDb.price != 0.0f){
            binding.tvcardprice.text = cardDb.price.toString() + "$"
        }
        else {
            binding.tvcardprice.text = "-"
        }

        if (cardDb.pokedexNumber != null){
            binding.tvpokedexnumber.text = "#" + cardDb.pokedexNumber.toString()
            println(cardDb.pokedexNumber.toString())
        }
        else {
            binding.tvpokedexnumber.text = "-"
        }

        binding.tvrarity.text = cardDb.rarity


        binding.cvcardlarge.setOnClickListener(){
            val url_tcg = cardDb.shop
            val uri: Uri = Uri.parse(url_tcg) // missing 'http://' will cause crashed

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


    }


}