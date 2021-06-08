package it.fasm.pokemoncard

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import it.fasm.pokemoncard.databinding.ActivityMainBinding
import kotlin.jvm.Throws


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var string: String = "ciao"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prova()
    }


    fun prova() {

        @Throws(AuthFailureError::class)
        fun getHeaders(): Map<String, String>? {
            //val params: MutableMap<String, String> =
            //    HashMap()
            //params["Content-Type"] = "application/json; charset=UTF-8"
            //params["token"] = "99967d70-c1ae-4dcb-a297-6d613706472d"
            val headers: HashMap<String, String> = HashMap()
            headers.put("X-Api-Key", "99967d70-c1ae-4dcb-a297-6d613706472d")
            return headers
        }

        val url = "https://api.pokemontcg.io/v2/cards"

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)


        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val prova = "Response: %s".format(response.toString())
                Log.w("prova", prova)
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
            }
        )

        queue.add(jsonObjectRequest)
    }
}