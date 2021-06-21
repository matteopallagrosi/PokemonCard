package it.fasm.pokemoncard.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.adapters.CardsAdapter
import it.fasm.pokemoncard.adapters.SeriesAdapter
import it.fasm.pokemoncard.databinding.FragmentCardsBinding
import it.fasm.pokemoncard.databinding.FragmentSearchBinding
import it.fasm.pokemoncard.model.Card
import org.json.JSONObject

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CardsAdapter
    private var cards = ArrayList<Card>()
    private var cardImages = HashMap<String , Bitmap>()

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var adapter = SeriesAdapter(requireContext())
        binding.rvSeries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSeries.adapter = adapter

    }

 */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        class GridSpacingItemDecoration(
                private val spanCount: Int,
                private val spacing: Int,
                private val includeEdge: Boolean
        ) :
                RecyclerView.ItemDecoration() {
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


        binding.rvcardsearch.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = CardsAdapter(cards, cardImages, requireContext())
        binding.rvcardsearch.adapter = adapter

        val spanCount = 3 // 3 columns

        val spacing = 20 // 50px

        val includeEdge = true
        binding.rvcardsearch.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))

        toSearch()

        return view
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun toSearch(){

        val text = binding.etnameinput.text
        binding.floatingActionButton.setOnClickListener(){
            println(text)
            val url = "https://api.pokemontcg.io/v2/cards?q=name:" + text
            setUICard(url)
        }
    }


    fun setUICard(url: String) {
        //var url = "https://api.pokemontcg.io/v2/cards?q=set.id:$set"

        val queue = Volley.newRequestQueue(requireContext())


        val jsonObjectRequest = object : StringRequest(
                Request.Method.GET, url,
                Response.Listener { response ->

                    var jo = JSONObject(response)
                    var ja = jo.getJSONArray("data")
                    println(ja.toString())

                    var gson = Gson()

                    val sType = object : TypeToken<ArrayList<Card>>() {}.type

                    cards.addAll(gson.fromJson<ArrayList<Card>>(ja.toString(), sType))
                    val cardBack = BitmapFactory.decodeResource(this.resources, R.drawable.card_back)
                    for (card in cards) {
                        cardImages[card.id] = cardBack
                    }
                    adapter.notifyDataSetChanged()
                    val requestQueue = Volley.newRequestQueue(requireContext())
                    for (card in cards) {
                        val imageRequest = ImageRequest(card.images.large, {
                            cardImages[card.id] = it
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