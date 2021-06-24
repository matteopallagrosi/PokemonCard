package it.fasm.pokemoncard.fragments

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.FragmentFavoritesBinding
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import it.fasm.pokemoncard.dbManager.DeckDb
import it.fasm.pokemoncard.viewModel.deckList
import kotlinx.coroutines.*
import kotlin.random.Random


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!
    private lateinit var vibe: Vibrator

    private val deckTypes = listOf("icon_tcgo_expanded", "icon_tcgo_legacy", "icon_tcgo_overall", "icon_tcgo_themedeck", "icon_tcgo_standard")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vibe = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        var job: Job = Job()
        var decklist: List<String> = listOf("")

        job = CoroutineScope(Dispatchers.IO).launch {
            val cardDao = CardDbDatabase.getDatabase(requireContext()).getCardDbDao()
            decklist = cardDao.decksaved()
        }
        runBlocking {
            job.join()
        }

        for (i in decklist) {
            println(i)
            addItem(i)
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val cancelText = "<font color='#000000'>Cancel</font>"
        var result: Long = 0
        var deckName: String = ""
        var job: Job
        binding.btnAdd.setOnClickListener() {
            var dialog = MaterialDialog(requireContext()).show {
                input { dialog, text ->
                    job = CoroutineScope(Dispatchers.IO).launch {
                        val cardDao = CardDbDatabase.getDatabase(requireContext()).getCardDbDao()
                        result = cardDao.addDeck(DeckDb(text.toString()))
                        deckName = text.toString()
                    }

                    runBlocking {
                        job.join()
                    }
                    if (result == -1L) {
                        var toast = Toast.makeText(requireContext(), "Name already exists", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.BOTTOM, 0, 200)
                        toast.show()
                    } else
                        addItem(deckName)
                }
                positiveButton(R.string.submit)
                title(R.string.deckname)
                negativeButton(text = Html.fromHtml(cancelText))
            }
        }
    }


    private fun addItem(deckName: String){

        val newView: LinearLayout = this.layoutInflater.inflate(R.layout.deck, null) as LinearLayout
        val num =  Random.nextInt(deckTypes.size)

        newView.children.forEach { cv->
            if (cv is CardView) {

                cv.children.forEach { it ->
                    if (it.id == R.id.tvdeckname && (it is TextView)) {
                        it.text = deckName
                    }
                    if (it.id == R.id.imageView14 && (it is ImageView)) {
                        val id = resources.getIdentifier(deckTypes[num], "drawable", requireContext().packageName)
                        it.setImageResource(id)
                    }
                    if (it.id == R.id.btndelete) {
                        it.setOnClickListener() {
                            removeItem(it, deckName)
                            println("ciao!")
                        }
                    }
                    cv.setOnLongClickListener(object : View.OnLongClickListener {
                        override fun onLongClick(v: View?): Boolean {
                            vibe.vibrate(80)
                            if (it.id == R.id.btndelete) {
                                if (it.visibility == View.GONE) it.visibility = View.VISIBLE
                                else it.visibility = View.GONE
                            }

                            return true
                        }
                    })
                }
            }
        }

        binding.layout.addView(newView, 0)
        println(newView.id)
        binding.layout.id
    }

    private fun removeItem(view: View, deckName: String) {
        binding.layout.removeView(view.parent.parent as LinearLayout)
        val cardDao = CardDbDatabase.getDatabase(requireContext()).getCardDbDao()
        CoroutineScope(Dispatchers.IO).launch {
            cardDao.removeDeck(DeckDb(deckName))
        }

    }


}