package it.fasm.pokemoncard.model

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
    val ancientTrait: Ability? = null
)

