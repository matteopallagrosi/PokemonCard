package it.matteopallagrosi.pokemoncard.model

import com.google.gson.annotations.SerializedName


data class TcgPlayerPrices (
        val normal: TcgPlayerValues,
        val holofoil: TcgPlayerValues,
        val reverseHolofoil: TcgPlayerValues,

        @SerializedName("1stEditionHolofoil")
        val firstEditionHolofoil: TcgPlayerValues,

        @SerializedName("1stEditionNormal")
        val firstEditionNormal: TcgPlayerValues,
        )