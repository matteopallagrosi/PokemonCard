package it.fasm.pokemoncard.fragments

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.util.DisplayMetrics
import android.view.*
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.FragmentCardsDeckBinding
import it.fasm.pokemoncard.dbManager.CardDb
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class CardsDeckFragment : Fragment() {

    private lateinit var binding: FragmentCardsDeckBinding
    private var cardList = listOf<CardDb>()
    private lateinit var cont: Context

    private lateinit var vibe: Vibrator
    var deck: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cont = requireContext()
        vibe = cont.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        setHasOptionsMenu(true)

        deck = arguments?.getString("deck")
       /* var job = CoroutineScope(Dispatchers.IO).launch {
            val cardDao = CardDbDatabase.getDatabase(cont).getCardDbDao()
            if (deck != null) {
                cardList = cardDao.getCardsDeck(deck!!)
            }
        }
        runBlocking {
            job.join()
        } */
    }

    private fun addItem(cardDb: CardDb){

        if (this.isAdded) {
            val newView: ConstraintLayout = this.layoutInflater.inflate(R.layout.card, null) as ConstraintLayout

            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            var width = displayMetrics.widthPixels

            if (width < 1080) {
                binding.layout.columnCount = 2
            }
            if (width < 720) {
                binding.layout.columnCount = 1
            }



            newView.children.forEach { c ->
                if (c.id == R.id.ivCardPref && c is ImageView) c.setImageBitmap(cardDb.image)
                c.setOnLongClickListener(object : View.OnLongClickListener {
                    override fun onLongClick(v: View?): Boolean {
                        vibe.vibrate(80)
                        newView.children.forEach {
                            if (it.id == R.id.btnremove) {
                                if (it.visibility == View.GONE) {
                                    it.visibility = View.VISIBLE
                                    it.setOnClickListener() {
                                        removeItem(newView, cardDb.id)
                                    }
                                } else {
                                    it.visibility = View.GONE
                                }

                            }
                        }
                        return true
                    }
                })

                c.setOnClickListener() { v ->
                    var activity = v.context as AppCompatActivity
                    var cardLargeFavoritesFragment = CardLargeFavoritesFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("card", cardDb)
                    cardLargeFavoritesFragment.arguments = bundle
                    activity.supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentHost, cardLargeFavoritesFragment)
                            .addToBackStack(null).commit();
                }


            }

            binding.layout.addView(newView, 0)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardsDeckBinding.inflate(inflater, container, false)


        /* for (card in cardList) {
            addItem(card)
        } */

        /*
        var parem: GridLayout.LayoutParams = GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f))

        binding.layout.children.forEach {it ->
            if (it.id == R.id.ivCard && it is ImageView) {
                it.layoutParams = parem
                parem.height = 100
                it.layoutParams = parem
                it.maxHeight = 80
                it.maxWidth = 80
            }
        }
         */


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            var job = CoroutineScope(Dispatchers.IO).launch {
                if(cardList.size == 0) {
                    val cardDao = CardDbDatabase.getDatabase(cont).getCardDbDao()
                    if (deck != null) {
                        cardList = cardDao.getCardsDeck(deck!!)
                    }
                }
                launch(Dispatchers.Main) {
                    binding.progressBar4.visibility = View.GONE
                    for (card in cardList) {
                        addItem(card)
                    }
                }
            }

    }

    private fun removeItem(view: View,id: String) {
        binding.layout.removeView(view)
        val cardDao = CardDbDatabase.getDatabase(requireContext()).getCardDbDao()
        CoroutineScope(Dispatchers.IO).launch {
            cardDao.deleteCard(id)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.order_menu, menu);

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.progressBar4.visibility = View.VISIBLE
        return when(item.itemId) {
            R.id.name ->{
                CoroutineScope(Dispatchers.IO).launch {
                    val cardDao = CardDbDatabase.getDatabase(cont).getCardDbDao()
                    cardList = cardDao.getOrderedByName(deck)

                /*runBlocking {
                    job.join()
                }*/
                    launch(Dispatchers.Main) {
                        binding.progressBar4.visibility = View.GONE
                        binding.layout.removeAllViews()
                        for (card in cardList) {
                            addItem(card)
                        }
                    }
                }
                true
            }
            R.id.hp -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val cardDao = CardDbDatabase.getDatabase(cont).getCardDbDao()
                    cardList = cardDao.getOrderedByHp(deck)

                    /*runBlocking {
                        job.join()
                    }*/
                    launch(Dispatchers.Main) {
                        binding.progressBar4.visibility = View.GONE
                        binding.layout.removeAllViews()
                        for (card in cardList) {
                            addItem(card)
                        }
                    }
                }
                true
            }
            R.id.price -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val cardDao = CardDbDatabase.getDatabase(cont).getCardDbDao()
                    cardList = cardDao.getOrderedByPrice(deck)

                    /*runBlocking {
                        job.join()
                    }*/
                    launch(Dispatchers.Main) {
                        binding.progressBar4.visibility = View.GONE
                        binding.layout.removeAllViews()
                        for (card in cardList) {
                            addItem(card)
                        }
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}