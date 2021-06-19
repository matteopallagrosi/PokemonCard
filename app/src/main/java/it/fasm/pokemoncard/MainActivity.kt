package it.fasm.pokemoncard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import it.fasm.pokemoncard.adapters.SeriesAdapter
import it.fasm.pokemoncard.databinding.ActivityMainBinding
import it.fasm.pokemoncard.fragments.FavoritesFragment
import it.fasm.pokemoncard.fragments.HomeFragment
import it.fasm.pokemoncard.fragments.SearchFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cardFragment = HomeFragment()
        val favoritesFragment = FavoritesFragment()
        val searchFragment = SearchFragment()

        makeCurrentFragment(cardFragment)

        binding.bottomNavigation.setOnNavigationItemReselectedListener {
            when (it){
                binding.ic_cards -> makeCurrentFragment()
                binding.ic_search -> makeCurrentFragment()
                binding.ic_favorites -> makeCurrentFragment()
            }
        }

        binding.scrollCards.isHorizontalScrollBarEnabled = false

        var adapter = SeriesAdapter(this)
        binding.rvSeries.layoutManager = LinearLayoutManager(this)
        binding.rvSeries.adapter = adapter

        //prova()
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper,fragment)
            commit()

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
                headers["X-Api-Key"] = "99967d70-c1ae-4dcb-a297-6d613706472d\n"
                return headers
            }
        }

        queue.add(jsonObjectRequest)


    }
}