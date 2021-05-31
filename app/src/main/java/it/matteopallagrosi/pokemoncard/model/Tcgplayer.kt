package it.matteopallagrosi.pokemoncard.model

data class Tcgplayer (
    val url: String,
    val updatedAt: String,
    val prices: TcgPlayerPrices,
        )