package it.fasm.pokemoncard.model

data class Card(
    val id: String,
    val name: String,
    val supertypes: SuperType,
    val subtypes: List<SubType>,
    val hp: Int?,
    val types: List<Type>?,
    val rules: List<String>?,
    val evolvesTo: List<String>?,
    val evolvesFrom: String?,
    val attacks: List<Attack>?,
    val weaknesses: List<Effect>?,
    val retreatCost: List<Type>?,
    val convertedRetreatCost: Int?,
    val set: CardSet,
    val number: String,
    val artist: String?,
    val rarity: String?,
    val nationalPokedexNumber: List<Int>?,
    val legalities: Legalities,
    val images: CardImage,
    val tcgplayer: Tcgplayer,
    val resistances: List<Effect>?,
    val ability: Ability?,
    val ancientTrait: Ability?
)

