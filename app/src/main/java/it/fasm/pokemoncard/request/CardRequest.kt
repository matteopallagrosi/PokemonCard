package it.fasm.pokemoncard.request

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.fasm.pokemoncard.model.Card
import org.json.JSONObject

class CardRequest {


    fun getCards(urlRequest: String, context: Context): ArrayList<Card> {

        var cards = ArrayList<Card>()
        var done = false

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)

        val url = "https://api.pokemontcg.io/v2/$urlRequest"


        val jsonObjectRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener{ response ->
                println(response.toString())

                cards = CardParsing.parse(response.toString())
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

        return cards
    }




}