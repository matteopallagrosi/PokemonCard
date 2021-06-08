package it.fasm.pokemoncard.request

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.fasm.pokemoncard.model.Card
import org.json.JSONObject

class CardParsing {

    companion object {
        fun parse(jsonData: String): ArrayList<Card> {

            var jo = JSONObject(jsonData)
            //var ja = jo.getJSONObject("data")

            var ja = jo.getJSONArray("data")
            var gson = Gson()

            val sType = object : TypeToken<ArrayList<Card>>() { }.type

            var cards = gson.fromJson<ArrayList<Card>>(ja.toString(), sType)
            return cards

        }

    }




}