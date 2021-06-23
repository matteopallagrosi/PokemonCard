package it.fasm.pokemoncard

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

        val card = arguments?.getString("card")

        setUIBigCard(card)
        return binding.root
    }

    /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardLargeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val card = intent.extras?.getString("card")

        setUIBigCard(card)
    } */

    private fun setUIBigCard(card: String?) {
        var url = "https://api.pokemontcg.io/v2/cards/$card"


        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this.context)


        val jsonObjectRequest = object : StringRequest(Request.Method.GET, url,
                Response.Listener{ response ->
                    println(response.toString())

                    var jo = JSONObject(response)
                    var ja = jo.getJSONObject("data")
                    //var ja = jo.getJSONArray("data")
                    println(ja.toString())

                    var gson = Gson()

                    val sType = object : TypeToken<Card>() { }.type

                    var card = gson.fromJson<Card>(ja.toString(), sType)
                    println(card)

                    setDescription(card)

                    val requestQueue = Volley.newRequestQueue(this.context)
                    val imageRequest = ImageRequest(card.images.large, {
                        binding.ivcardlarge.setImageBitmap(it)
                        binding.ivcardlarge.visibility = View.VISIBLE
                        println("OK")

                    }, 0, 0,
                            ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                            { error ->
                                Log.e("Volley", error.toString())
                            })
                    requestQueue.add(imageRequest)
                },
                Response.ErrorListener { error ->
                    println("Non ha funzionato")
                }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-Api-Key"] = "99967d70-c1ae-4dcb-a297-6d613706472d\n"
                return headers
            }
        }

        queue.add(jsonObjectRequest)
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
            val uri: Uri = Uri.parse(url_tcg) // missing 'http://' will cause crashed

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


    }


}