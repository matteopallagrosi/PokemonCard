package it.fasm.pokemoncard.model

import com.google.gson.annotations.SerializedName


data class TcgPlayerPrices (
        val normal: TcgPlayerValues = TcgPlayerValues(),
        val holofoil: TcgPlayerValues = TcgPlayerValues(),
        val reverseHolofoil: TcgPlayerValues = TcgPlayerValues(),

        @SerializedName("1stEditionHolofoil")
        val firstEditionHolofoil: TcgPlayerValues = TcgPlayerValues(),

        @SerializedName("1stEditionNormal")
        val firstEditionNormal: TcgPlayerValues = TcgPlayerValues(),
        )