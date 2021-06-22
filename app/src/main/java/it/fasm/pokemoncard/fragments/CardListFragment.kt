package it.fasm.pokemoncard.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
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
import it.fasm.pokemoncard.databinding.FragmentCardListBinding
import it.fasm.pokemoncard.model.Card
import org.json.JSONObject

class CardListFragment : Fragment() {

    private lateinit var binding: FragmentCardListBinding
    private lateinit var adapter: CardsAdapter
    private var cards = ArrayList<Card>()
    private var cardImages = HashMap<String, Bitmap>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCardListBinding.inflate(layoutInflater, container, false)

        val view = binding.root

        //val set = intent.extras.getString("set")

        // NON CREDO FUNZIONI
        val bundle = Bundle()
        bundle.putString("set", "From MainActivity")
        //set Fragmentclass Arguments
        val fragobj = CardListFragment()
        fragobj.arguments = bundle


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


        binding.rvCards.layoutManager = GridLayoutManager(context, 3)
        adapter = CardsAdapter(cards, cardImages, requireContext())
        binding.rvCards.adapter = adapter

        val spanCount = 3 // 3 columns

        val spacing = 20 // 50px

        val includeEdge = true
        binding.rvCards.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount,
                spacing,
                includeEdge
            )
        )

        setUICard(bundle.toString())
        return view
    }
    fun setUICard(set: String?) {
        var url = "https://api.pokemontcg.io/v2/cards?q=set.id:$set"

        val queue = Volley.newRequestQueue(context)


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
                val requestQueue = Volley.newRequestQueue(context)
                for (card in cards) {
                    val imageRequest = ImageRequest(card.images.large, {
                        cardImages[card.id] = it
                        println("OK")
                        adapter.notifyDataSetChanged()
                        binding.tvnumbercards.text = cards.size.toString()
                        binding.tvdate.text = cards[0].set.releaseDate.toString()

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

        jsonObjectRequest.setRetryPolicy(object : RetryPolicy {
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