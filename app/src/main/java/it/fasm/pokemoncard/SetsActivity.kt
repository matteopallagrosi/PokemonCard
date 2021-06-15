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
import it.fasm.pokemoncard.adapters.SetsAdapter
import it.fasm.pokemoncard.databinding.ActivitySetsBinding
import it.fasm.pokemoncard.model.CardSet
import org.json.JSONObject

class SetsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serie = intent.extras?.getString("serie")
        println(serie)

        setUI(serie)


        //binding.rvSets.layoutManager = GridLayoutManager(this, 2)
        //binding.rvSets.adapter = SetsAdapter()
    }

    fun setUI(serie:String?) {
        var url = "https://api.pokemontcg.io/v2/sets?q=series:$serie"

        val queue = Volley.newRequestQueue(this)


        val jsonObjectRequest = object : StringRequest(Request.Method.GET, url,
                Response.Listener{ response ->

                    var jo = JSONObject(response)
                    var ja = jo.getJSONArray("data")
                    println(ja.toString())

                    var gson = Gson()

                    val sType = object : TypeToken<List<CardSet>>() { }.type

                    var sets = gson.fromJson<List<CardSet>>(ja.toString(), sType)
                    var logos = ArrayList<Bitmap>()
                    val requestQueue = Volley.newRequestQueue(this)
                    for(set in sets) {
                        val imageRequest = ImageRequest(set.images.logo, {
                            logos.add(it)
                            println("OK")
                            if (logos.size == sets.size) {
                                binding.rvSets.layoutManager = GridLayoutManager(this, 2)
                                binding.rvSets.adapter = SetsAdapter(sets,logos)
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