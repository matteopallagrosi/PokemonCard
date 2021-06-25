package it.fasm.pokemoncard.adapters

import android.content.Context
import android.os.Bundle
import android.os.Vibrator

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import it.fasm.pokemoncard.R
import it.fasm.pokemoncard.databinding.CenterLayoutBinding
import it.fasm.pokemoncard.databinding.FragmentCardsBinding
import it.fasm.pokemoncard.dbManager.CardDb
import it.fasm.pokemoncard.dbManager.CardDbDao
import it.fasm.pokemoncard.dbManager.CardDbDatabase
import it.fasm.pokemoncard.fragments.CardsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class CenterZoomAdapter(val context: Context, var fragmentBinding: FragmentCardsBinding, var cards : List<CardDb>): RecyclerView.Adapter<CenterZoomAdapter.ViewHolder>() {


    private val centerImage = intArrayOf(R.drawable.pokemon_1, R.drawable.pokemon_2,R.drawable.pokemon_3,
            R.drawable.pokemon_4,R.drawable.pokemon_5,
            R.drawable.pokemon_6,R.drawable.pokemon_7,R.drawable.pokemon_8,R.drawable.pokemon_9)

    inner class ViewHolder(binding: CenterLayoutBinding ): RecyclerView.ViewHolder(binding.root) {

        var card = binding.root
        var ivcenter = binding.ivCenter
        var btnDel = binding.btnDel

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CenterLayoutBinding.inflate(layoutInflater, parent, false)



        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //lateinit var vibe: Vibrator

        var cardDao : CardDbDao? = null
        var job = CoroutineScope(Dispatchers.IO).launch {
            cardDao = CardDbDatabase.getDatabase(context).getCardDbDao()
        }
        runBlocking{
            job.join()
        }

        if (cards.isNotEmpty()){

            holder.ivcenter.setImageBitmap(cards[position].image)
        }else{
            holder.ivcenter.setImageResource(centerImage[position])
        }
        //var state = false



        //holder.ivcenter.setImageBitmap(cards[position].image)


        var state = false
        fragmentBinding.rvSeries.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
                return state
            }
        })



        var pos = position +1
        val id = context.resources.getIdentifier("pokemon$pos", "drawable", context.packageName)



        holder.ivcenter.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    //vibe.vibrate(80)
                    if (holder.btnDel.visibility == View.GONE) holder.btnDel.visibility = View.VISIBLE
                    else holder.btnDel.visibility = View.GONE

                    return true
                }
            })

        holder.btnDel.setOnClickListener(){
            var job = CoroutineScope(Dispatchers.IO).launch {cardDao?.deleteCard(cards[position].id)        }
            runBlocking{
                job.join()
            }
            holder.ivcenter.visibility= View.GONE
            holder.btnDel.visibility = View.GONE
                    }

        holder.ivcenter.setOnClickListener() {
            if (cards.isEmpty()){
                fragmentBinding.ivcardforeground.setImageResource(id)}
            else{
                fragmentBinding.ivcardforeground.setImageBitmap(cards[position].image)
            }

            fragmentBinding.ivcardforeground.visibility = View.VISIBLE
            //fragmentBinding.floatingActionButton3.visibility = View.VISIBLE
            //state = true
        }

        fragmentBinding.ivcardforeground.setOnClickListener(){
            fragmentBinding.floatingActionButton3.visibility  =View.INVISIBLE
            fragmentBinding.ivcardforeground.visibility = View.INVISIBLE
        }

        /*fragmentBinding.floatingActionButton3.setOnClickListener(){
            fragmentBinding.floatingActionButton3.visibility  =View.INVISIBLE
            fragmentBinding.ivcardforeground.visibility = View.INVISIBLE
            fragmentBinding.rvSeries.suppressLayout(false)
            println("fahsdifhsduhgidhg")
            //state = false
        }*/

    }



    override fun getItemCount(): Int {
       if(cards.isEmpty()){
        return centerImage.size}
        return cards.size
    }

}

