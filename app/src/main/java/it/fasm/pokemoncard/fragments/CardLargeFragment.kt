package it.fasm.pokemoncard.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.fasm.pokemoncard.databinding.FragmentCardLargeBinding
import it.fasm.pokemoncard.databinding.FragmentCardListBinding
import it.fasm.pokemoncard.model.Card
import org.json.JSONObject


class CardLargeFragment : Fragment() {

    private lateinit var binding: FragmentCardLargeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCardLargeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val card = arguments?.getSerializable("card") as Card
        setUIBigCard(card)
        super.onActivityCreated(savedInstanceState)
    }

    private fun setUIBigCard(card: Card) {
        setDescription(card)
        binding.ivcardlarge.setImageBitmap(card.imageDownloaded)
        binding.ivcardlarge.visibility = View.VISIBLE

    }

    private fun setDescription(card: Card) {

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

        if (card.nationalPokedexNumbers?.get(0) != null){
            binding.tvpokedexnumber.text = "#" + card.nationalPokedexNumbers?.get(0).toString()
            println(card.nationalPokedexNumbers?.get(0).toString())
        }
        else {
            binding.tvpokedexnumber.text = "-"
        }

        binding.tvrarity.text = card.rarity


        binding.cvcardlarge.setOnClickListener(){
            val url_tcg = card.tcgplayer.url
            val uri: Uri = Uri.parse(url_tcg)

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


    }


}