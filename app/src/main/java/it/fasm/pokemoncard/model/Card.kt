package it.fasm.pokemoncard.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

data class Card(
    val id: String = "",
    val name: String = "",
    val supertypes: SuperType = SuperType.ENERGY,
    val subtypes: List<SubType>? = null,
    val hp: Int? = null,
    val types: List<Type>? = null,
    val rules: List<String>? = null,
    val evolvesTo: List<String>? = null,
    val evolvesFrom: String? = null,
    val attacks: List<Attack>? = null,
    val weaknesses: List<Effect>? = null,
    val retreatCost: List<Type>? = null,
    val convertedRetreatCost: Int? = null,
    val set: CardSet = CardSet(),
    val number: String = "",
    val artist: String? = "",
    val rarity: String? = "",
    val nationalPokedexNumbers: List<Int>? = null,
    val legalities: Legalities = Legalities(),
    val images: CardImage = CardImage(),
    val tcgplayer: Tcgplayer = Tcgplayer(),
    val resistances: List<Effect>? = null,
    val ability: Ability? = null,
    val ancientTrait: Ability? = null,
    var favorites: Boolean = false,
    var downloaded: Boolean = false,
    var imageDownloaded: Bitmap? = null
) : java.io.Serializable

