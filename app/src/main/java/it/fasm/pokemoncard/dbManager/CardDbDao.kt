package it.fasm.pokemoncard.dbManager

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CardDbDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun addCard(card: CardDb)

    @Query ("DELETE FROM card WHERE id = :id")
    fun deleteCard(id: String)

    @Query ("SELECT COUNT(*) FROM card WHERE id = :id")
    fun checkCard(id: String): Int

    @Query ("SELECT COUNT(*) FROM card WHERE idSet = :idset")
    fun numFavInSet(idset: String): Int

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun addDeck(deck: DeckDb): Long

    @Query ("SELECT name FROM  deckdb")
    fun decksaved(): List<String>

    @Delete
    fun removeDeck(deck: DeckDb)

    @Query("SELECT * FROM card WHERE deck = :deckName")
    fun getCardsDeck(deckName: String): MutableList<CardDb>

    @Query ("DELETE FROM card WHERE deck = :deckName")
    fun removeCardDeck(deckName: String)

    @Query ("SELECT COUNT(*) FROM card WHERE deck = :deckName")
    fun numFavInDeck(deckName: String): Int

    @Query("SELECT * FROM card")
    fun getCards(): List<CardDb>

    @Query("SELECT * FROM card LIMIT 8")
    fun getLimitedCards(): List<CardDb>

    @Query("SELECT * FROM card WHERE deck = :deckName ORDER BY name DESC")
    fun getOrderedByName(deckName: String?) : MutableList<CardDb>

    @Query("SELECT * FROM card WHERE deck = :deckName ORDER BY hp DESC")
    fun getOrderedByHp(deckName: String?) : MutableList<CardDb>

    @Query("SELECT * FROM card WHERE deck = :deckName ORDER BY price ASC")
    fun getOrderedByPrice(deckName: String?) : MutableList<CardDb>
}