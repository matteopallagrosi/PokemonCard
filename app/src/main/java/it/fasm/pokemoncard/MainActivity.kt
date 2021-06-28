package it.fasm.pokemoncard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.fasm.pokemoncard.adapters.SeriesAdapter
import it.fasm.pokemoncard.adapters.SetsAdapter
import it.fasm.pokemoncard.databinding.ActivityMainBinding
import it.fasm.pokemoncard.fragments.*

class MainActivity : AppCompatActivity(), SeriesAdapter.OnSerieClickListener, SetsAdapter.OnSetClickListener {

    private lateinit var binding:ActivityMainBinding
    private lateinit var bottomNavigationView:  BottomNavigationView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        //val searchFragment = SearchFragment()
        //val cardFragment = CardsFragment()
        //val favoritesFragment = FavoritesFragment()

        bottomNavigationView.selectedItemId = R.id.cardsFragment
        val cardFragment = CardsFragment()
        val searchFragment = SearchFragment()
        val favoritesFragment = FavoritesFragment()


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){

                R.id.cardsFragment-> {
                    supportFragmentManager.findFragmentByTag("CARDLISTFRAGMENT")?.onDestroy()
                    setCurrentFragment(cardFragment, "home")
                }
                R.id.searchFragment->setCurrentFragment(searchFragment, "search")
                R.id.favoritesFragment->setCurrentFragment(favoritesFragment, "favorites")

            }
            true
        }

        /* val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentHost) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController) */


        //cambia la barra superiore in base al fragment
        /* val appBarConfig = AppBarConfiguration(
            setOf(
                R.id.cardsFragment,
                R.id.searchFragment,
                R.id.favoritesFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfig) */



        //prova()
    }

    private fun setCurrentFragment(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentHost, fragment).addToBackStack(name).commit()
        }
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

    override fun onSerieClick(serie: String) {
        println("eseguito!")
        val setsFragment = SetsFragment()
        val bundle = bundleOf("serie" to serie)
        setsFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentHost, setsFragment, "SETSFRAGMENT")
           .addToBackStack("sets").commit();
    }

    override fun onSetClick(set: String) {
        println("lanciato")
        val cardListFragment = CardListFragment()
        val bundle = bundleOf("set" to set)
        cardListFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentHost, cardListFragment, "CARDLISTFRAGMENT").addToBackStack("cardList").commit();
    }
}


