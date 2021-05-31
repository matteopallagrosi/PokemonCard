package it.fasm.pokemoncard.model

data class Attack(
    val name: String,
    val cost: List<Type>?,
    val convertedEnergyCost: Int,
    val damage: String?,
    val text: String?,
)