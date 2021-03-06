package it.fasm.pokemoncard

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.fasm.pokemoncard.adapters.SeriesAdapter
import it.fasm.pokemoncard.databinding.ActivityMainBinding
import it.fasm.pokemoncard.model.Card
import org.json.JSONObject
import kotlin.jvm.Throws


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scrollCards.isHorizontalScrollBarEnabled = false

        var adapter = SeriesAdapter(this)
        binding.rvSeries.layoutManager = LinearLayoutManager(this)
        binding.rvSeries.adapter = adapter

        //prova()
    }


    fun prova() {

        //val url = "https://api.pokemontcg.io/v2/cards/xy7-54"
        //val url = "https://api.pokemontcg.io/v2/cards?q=rarity:\"Rare Holo\""
        var url = "https://api.pokemontcg.io/v2/sets?q=series:XY"

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)


        val jsonObjectRequest = object : StringRequest(Request.Method.GET, url,
            Response.Listener{ response ->
                println(response.toString())

                /*var jo = JSONObject(response)
                //var ja = jo.getJSONObject("data")
                var ja = jo.getJSONArray("data")
                println(ja.toString())

                var gson = Gson()

                val sType = object : TypeToken<List<Card>>() { }.type

                var cards = gson.fromJson<List<Card>>(ja.toString(), sType)
                println(cards[0])*/
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