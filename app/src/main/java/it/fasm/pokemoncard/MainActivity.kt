package it.fasm.pokemoncard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.fasm.pokemoncard.databinding.ActivityMainBinding
import it.fasm.pokemoncard.fragments.CardsFragment
import it.fasm.pokemoncard.fragments.FavoritesFragment
import it.fasm.pokemoncard.fragments.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val searchFragment = SearchFragment()
        val cardFragment = CardsFragment()
        val favoritesFragment = FavoritesFragment()

        bottomNavigationView.selectedItemId = R.id.cardsFragment


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.cardsFragment->setCurrentFragment(cardFragment)
                R.id.searchFragment->setCurrentFragment(searchFragment)
                R.id.favoritesFragment->setCurrentFragment(favoritesFragment)

            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentHost,fragment)
                commit()
            }


}