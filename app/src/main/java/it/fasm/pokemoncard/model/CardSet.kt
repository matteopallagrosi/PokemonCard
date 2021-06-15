package it.fasm.pokemoncard.model

data class CardSet(
    val id: String = "",
    val name: String = "",
    val series: String = "",
    val printedTotal: Int = 0,
    val total: Int = 0,
    val legalities: Legalities = Legalities(),
    val ptcgoCode: String? = "",
    val releaseDate: String = "",
    val updatedAt: String = "",
    val images: SetImage = SetImage(),
)