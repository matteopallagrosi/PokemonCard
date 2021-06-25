package it.fasm.pokemoncard


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.fasm.pokemoncard.adapters.CardsAdapter
import it.fasm.pokemoncard.adapters.SetsAdapter
import it.fasm.pokemoncard.databinding.FragmentCardListBinding
import it.fasm.pokemoncard.databinding.FragmentSetsBinding
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import it.fasm.pokemoncard.dbManager.DeckDb
import it.fasm.pokemoncard.model.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject


class CardListFragment : Fragment() {

    private lateinit var binding: FragmentCardListBinding
    private lateinit var adapter: CardsAdapter
    private var cards = ArrayList<Card>()
    private var cardImages = HashMap<String ,Bitmap>()
    private var numPref = ""
    private var numberCards = ""
    private var releaseDate = ""
    private var deckList =  listOf("")
    private lateinit var cont: Context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCardListBinding.inflate(layoutInflater, container, false)



        //val set = arguments?.getString("set")
        if (cards.size == 0){
            binding.progressBar2.visibility = View.VISIBLE
        }

        class GridSpacingItemDecoration(
                private val spanCount: Int,
                private val spacing: Int,
                private val includeEdge: Boolean
        ) :
                ItemDecoration() {
            override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
            ) {

                val position = parent.getChildAdapterPosition(view) // item position
                val column = position % spanCount // item column
                if (includeEdge) {
                    outRect.left =
                            spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right =
                            (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                    if (position < spanCount) { // top edge
                        outRect.top = spacing
                    }
                    outRect.bottom = spacing // item bottom
                } else {
                    outRect.left =
                            column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                    outRect.right =
                            spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                    if (position >= spanCount) {
                        outRect.top = spacing // item top
                    }
                }
            }
        }

        binding.rvCards.layoutManager = GridLayoutManager(cont, 3)
        adapter = CardsAdapter(cards, cardImages, requireContext(), deckList)
        binding.rvCards.adapter = adapter

        val spanCount = 3 // 3 columns

        val spacing = 20 // 50px

        val includeEdge = true
        binding.rvCards.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        binding.tvnumbercards.text = numberCards
        binding.tvdate.text = releaseDate
        binding.tvpreferites.text = numPref
        //setUICard(set)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        cont = requireContext()
        CoroutineScope(Dispatchers.IO).launch {
            val cardDao = CardDbDatabase.getDatabase(cont).getCardDbDao()
            deckList = cardDao.decksaved()
        }

        val set = arguments?.getString("set")
        setUICard(set)
        super.onCreate(savedInstanceState)
    }


    fun setUICard(set: String?) {
        var url = "https://api.pokemontcg.io/v2/cards?q=set.id:$set"

        val queue = Volley.newRequestQueue(cont)


        val jsonObjectRequest = object : StringRequest(
                Request.Method.GET, url,
                Response.Listener { response ->

                    var jo = JSONObject(response)
                    var ja = jo.getJSONArray("data")
                    println(ja.toString())

                    var gson = Gson()

                    val sType = object : TypeToken<ArrayList<Card>>() {}.type
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        val cardDao = CardDbDatabase.getDatabase(cont).getCardDbDao()
                        if (set != null){
                            numPref = cardDao.numFavInSet(set).toString()
                        }
                    }
                    runBlocking {
                        job.join()
                    }
                    cards.addAll(gson.fromJson<ArrayList<Card>>(ja.toString(), sType))
                    val cardBack = BitmapFactory.decodeResource(cont.resources, R.drawable.card_back)
                    for (card in cards) {
                        cardImages[card.id] = cardBack
                    }
                    numberCards = cards.size.toString()
                    releaseDate = cards[0].set.releaseDate
                    binding.tvnumbercards.text = numberCards
                    binding.tvdate.text = releaseDate
                    binding.tvpreferites.text = numPref

                    binding.progressBar2.visibility = View.INVISIBLE

                    adapter.notifyDataSetChanged()
                    val requestQueue = Volley.newRequestQueue(cont)
                    for (card in cards) {
                        val imageRequest = ImageRequest(card.images.large, {
                            cardImages[card.id] = it
                            card.imageDownloaded = it
                            println("OK")
                            card.downloaded = true
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

        jsonObjectRequest.setRetryPolicy( object : RetryPolicy {
            override fun getCurrentTimeout(): Int {
                return 50000
            }

            override fun getCurrentRetryCount(): Int {
                return 50000
            }

            override fun retry(error: VolleyError?) {
            }


        })

        queue.add(jsonObjectRequest)

    }

}