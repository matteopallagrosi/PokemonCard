package it.fasm.pokemoncard.fragments

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.adapters.CardsAdapter
import it.fasm.pokemoncard.databinding.FragmentSearchBinding
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import it.fasm.pokemoncard.model.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CardsAdapter
    private var cards = ArrayList<Card>()
    private var cardImages = HashMap<String , Bitmap>()

    private lateinit var queue : RequestQueue
    private lateinit var requestQueue : RequestQueue
    private var deckList =  listOf("")

    val TAG1 = "CARD"
    val TAG2 = "IMAGE"


    override fun onCreate(savedInstanceState: Bundle?) {
        CoroutineScope(Dispatchers.IO).launch {
            val cardDao = CardDbDatabase.getDatabase(requireContext()).getCardDbDao()
            deckList = cardDao.decksaved()
    }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        queue = Volley.newRequestQueue(requireContext())
        requestQueue = Volley.newRequestQueue(requireContext())

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
        adapter = CardsAdapter(cards, cardImages, requireContext(), deckList)
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
        var text : String
        var minhp : String
        var rarity: String

        binding.seekBar.setProgress(0)
        binding.seekBar.incrementProgressBy(10)
        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var value = progress%10
                value = progress - value
                binding.tvHp.text = value.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })


        val spinner = binding.spinRarity
        ArrayAdapter.createFromResource(requireContext(),
                R.array.rarity_string,
                android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = it
        }

        class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }



        binding.floatingActionButton.setOnClickListener(){
            var id_spin = binding.spinRarity.getSelectedItemId().toInt()
            rarity = binding.spinRarity.getItemAtPosition(id_spin).toString()
            text = binding.etnameinput.text.toString()
            minhp = binding.tvHp.text.toString()
            println(rarity)
            var url = "https://api.pokemontcg.io/v2/cards?q="

            if (text != ""){
                url = url + "name:" + text + "*"
            }
            if (rarity != "All"){
                url = url + " !rarity:" + "\"" + rarity + "\""
            }
            url = url + " hp:" + "[" + minhp + " TO *]"

            //url = "https://api.pokemontcg.io/v2/cards?q=!rarity:\"Rare\""
            binding.progressBar.visibility = View.VISIBLE
            println(url)
            setUICard(url)
        }
    }


    fun setUICard(url: String) {
        //var url = "https://api.pokemontcg.io/v2/cards?q=set.id:$set"

        queue.cancelAll(TAG1)
        requestQueue.cancelAll(TAG2)


        val jsonObjectRequest = object : StringRequest(
                Request.Method.GET, url,
                Response.Listener { response ->

                    var jo = JSONObject(response)
                    var ja = jo.getJSONArray("data")
                    println(ja.toString())

                    var gson = Gson()

                    val sType = object : TypeToken<ArrayList<Card>>() {}.type

                    cardImages.clear()
                    cards.clear()
                    cards.addAll(gson.fromJson<ArrayList<Card>>(ja.toString(), sType))
                    val cardBack = BitmapFactory.decodeResource(requireContext().resources, R.drawable.card_back)
                    for (card in cards) {
                        cardImages[card.id] = cardBack
                    }

                    binding.progressBar.visibility = View.INVISIBLE

                    adapter.notifyDataSetChanged()
                    println("size"+cards.size)
                    if (cards.size == 0){
                        binding.tvNoresult.visibility = View.VISIBLE
                    }
                    else {
                        binding.tvNoresult.visibility = View.INVISIBLE
                    }

                    for (card in cards) {
                        val imageRequest = ImageRequest(card.images.large, {
                            cardImages[card.id] = it
                            adapter.notifyDataSetChanged()

                        }, 0, 0,
                                ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                                { error ->
                                    Log.e("Volley", error.toString())
                                })
                        imageRequest.tag = TAG2
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
        jsonObjectRequest.tag = TAG1
        queue.add(jsonObjectRequest)

    }


}