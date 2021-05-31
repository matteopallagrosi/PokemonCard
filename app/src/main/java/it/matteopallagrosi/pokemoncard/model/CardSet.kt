package it.matteopallagrosi.pokemoncard.model

data class CardSet(
    val id: String,
    val name: String,
    val series: String,
    val printedTotal: Int,
    val total: Int,
    val legalities: Legalities,
    val ptcgoCode: String?,
    val releaseDate: String,
    val updatedAt: String,
    val images: SetImage,
)