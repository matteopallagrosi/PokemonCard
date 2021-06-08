package it.fasm.pokemoncard

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.fasm.pokemoncard.databinding.ActivityHomeBinding
import it.fasm.pokemoncard.databinding.ActivityMainBinding
import it.fasm.pokemoncard.model.Card
import org.json.JSONObject
import kotlin.jvm.Throws


class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding

    private var response = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prova()
    }


    fun prova() {

        //val url = "https://api.pokemontcg.io/v2/cards/xy7-54"
        val url = "https://api.pokemontcg.io/v2/sets"

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
                println(cards[1].id) */
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