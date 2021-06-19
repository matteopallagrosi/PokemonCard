package it.fasm.pokemoncard

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.fasm.pokemoncard.databinding.ActivityCardLargeBinding
import it.fasm.pokemoncard.model.Card
import org.json.JSONObject


class CardLargeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardLargeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardLargeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val card = intent.extras?.getString("card")

        setUIBigCard(card)
    }

    private fun setUIBigCard(card: String?) {
        var url = "https://api.pokemontcg.io/v2/cards/$card"


        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)


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

                    val requestQueue = Volley.newRequestQueue(this)
                    val imageRequest = ImageRequest(card.images.large, {
                        binding.ivcardlarge.setImageBitmap(it)
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

        if (card.tcgplayer.prices.holofoil.mid != null) {
            binding.tvcardprice.text = card.tcgplayer.prices.holofoil.mid.toString() + "$"
        } else {
            binding.tvcardprice.text = "-"
        }


        if (card.nationalPokedexNumber != null){
            binding.tvpokedexnumber.text = card.nationalPokedexNumber[0].toString()
        }

        binding.cvcardlarge.setOnClickListener(){
            val url_tcg = card.tcgplayer.url
            val uri: Uri = Uri.parse(url_tcg) // missing 'http://' will cause crashed

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


    }


}