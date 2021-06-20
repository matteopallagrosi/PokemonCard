package it.fasm.pokemoncard.model

enum class Type(internal var text: String? = null) {
    Colorless,
    Darkness,
    Dragon,
    Fairy,
    Fighting,
    Fire,
    Grass,
    Lightning,
    Metal,
    Psychic,
    Water,
    UNKNOWN;

    val displayName: String
        get() = text ?: name.capitalize()

    companion object {
        val VALUES by lazy { values() }

        fun find(text: String): Type {
            val type = VALUES.find { it.name.equals(text, true) } ?: UNKNOWN
            if (type == UNKNOWN) {
                type.text = text
            }
            return type
        }
    }
}
