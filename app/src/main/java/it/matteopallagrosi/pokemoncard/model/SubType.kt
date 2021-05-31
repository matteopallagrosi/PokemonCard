package it.matteopallagrosi.pokemoncard.model

enum class SubType(internal var text: String? = null) {
    BABY("Baby"),
    EX("EX"),
    SPECIAL("Special"),
    RESTORED("Restored"),
    RAPID_STRIKE("Rapid Strike"),
    SINGLE_STRIKE("Single Strike"),
    LEVEL_UP("Level Up"),
    MEGA("MEGA"),
    TECHNICAL_MACHINE("Technical Machine"),
    ITEM("Item"),
    STADIUM("Stadium"),
    SUPPORTER("Supporter"),
    STAGE_1("Stage 1"),
    STAGE_2("Stage 2"),
    GX("GX"),
    POKEMON_TOOL("Pokémon Tool"),
    POKEMON_TOO_F("Pokemon Tool F"),
    BASIC("Basic"),
    LEGEND("LEGEND"),
    BREAK("BREAK"),
    ROCKETS_SECRET_MACHINE("Rocket's Secret Machine"),
    GOLDENROAD_GAME_CORNER("Goldenroad Game Corner"),
    TAG_TEAM("TAG TEAM"),
    V("V"),
    VMAX("VMAX"),
    UNKNOWN;

    val displayName: String
        get() = text ?: name.toLowerCase().capitalize()

    companion object {
        private val VALUES by lazy { values() }

        fun find(text: String?): SubType {
            val subtype = VALUES.find { it.text.equals(text, true) } ?: UNKNOWN
            if (subtype == UNKNOWN) {
                subtype.text = text
            }
            return subtype
        }
    }
}