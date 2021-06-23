package it.fasm.pokemoncard

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
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
import it.fasm.pokemoncard.databinding.FragmentSetsBinding
import it.fasm.pokemoncard.model.Card
import it.fasm.pokemoncard.model.CardSet
import org.json.JSONObject

class SetsFragment : Fragment() {

    private lateinit var binding: FragmentSetsBinding
    private lateinit var adapter: SetsAdapter
    private var sets = ArrayList<CardSet>()
    private var logos = HashMap<String, Bitmap>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSetsBinding.inflate(layoutInflater, container, false)

        val serie = arguments?.getString("serie")

        binding.rvSets.layoutManager = GridLayoutManager(this.context, 2)
        adapter = SetsAdapter(sets, logos, requireContext())
        binding.rvSets.adapter = adapter

        setUI(serie)

        return binding.root
    }

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serie = intent.extras?.getString("serie")
        binding.rvSets.layoutManager = GridLayoutManager(this, 2)
        adapter = SetsAdapter(sets, logos, this)
        binding.rvSets.adapter = adapter

        setUI(serie)


        //binding.rvSets.layoutManager = GridLayoutManager(this, 2)
        //binding.rvSets.adapter = SetsAdapter()
    } */

    fun setUI(serie:String?) {
        var url = "https://api.pokemontcg.io/v2/sets?q=series:$serie"

        val queue = Volley.newRequestQueue(this.context)


        val jsonObjectRequest = object : StringRequest(Request.Method.GET, url,
                Response.Listener{ response ->

                    var jo = JSONObject(response)
                    var ja = jo.getJSONArray("data")
                    println(ja.toString())

                    var gson = Gson()

                    val sType = object : TypeToken<ArrayList<CardSet>>() { }.type

                    sets.addAll(gson.fromJson<ArrayList<CardSet>>(ja.toString(), sType))
                    val requestQueue = Volley.newRequestQueue(this.context)
                    for(set in sets) {
                        val imageRequest = ImageRequest(set.images.logo, {
                            logos[set.id] = it
                            println("OK")
                            adapter.notifyDataSetChanged()
                        }, 0, 0,
                                ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                                { error ->
                                    Log.e("Volley", error.toString())
                                })

                        requestQueue.add(imageRequest)
                    }
                },
                Response.ErrorListener { error ->
                    Log.e("Volley", error.toString())
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



}