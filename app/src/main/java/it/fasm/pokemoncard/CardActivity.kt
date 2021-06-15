package it.fasm.pokemoncard

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.fasm.pokemoncard.adapters.CardsAdapter
import it.fasm.pokemoncard.adapters.SetsAdapter
import it.fasm.pokemoncard.databinding.ActivityCardBinding
import it.fasm.pokemoncard.model.Card
import it.fasm.pokemoncard.model.CardSet
import org.json.JSONObject

class CardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val set = intent.extras?.getString("set")
        println(set)

        setUICard(set)
    }

    fun setUICard(set: String?) {
        var url = "https://api.pokemontcg.io/v2/cards?q=set.id:$set"

        val queue = Volley.newRequestQueue(this)


        val jsonObjectRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener{ response ->

                var jo = JSONObject(response)
                var ja = jo.getJSONArray("data")
                println(ja.toString())

                var gson = Gson()

                val sType = object : TypeToken<List<Card>>() { }.type

                var cards = gson.fromJson<List<Card>>(ja.toString(), sType)
                var cardImages = ArrayList<Bitmap>()
                val requestQueue = Volley.newRequestQueue(this)
                for(card in cards) {
                    val imageRequest = ImageRequest(card.images.small, {
                        cardImages.add(it)
                        println("OK")
                        if (cardImages.size == cards.size) {
                            binding.rvCards.layoutManager = GridLayoutManager(this, 3)
                            binding.rvCards.adapter = CardsAdapter(cards, cardImages, this)
                        }

                    }, 0, 0,
                        ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                        {
                            println("Non ha funzionato")
                        })
                    requestQueue.add(imageRequest)
                }
            },
            Response.ErrorListener { error ->
                println("Non ha funzionato")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-Api-Key"] = "66e2513d-af69-45bc-9cd2-38f7a75a8326"
                return headers
            }
        }

        queue.add(jsonObjectRequest)

    }

}