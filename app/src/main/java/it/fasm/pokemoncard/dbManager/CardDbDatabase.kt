package it.fasm.pokemoncard.dbManager

import android.content.Context
import androidx.room.*


@Database(entities = [CardDb::class, DeckDb::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class CardDbDatabase : RoomDatabase (){

    companion object {
        private var db: CardDbDatabase? = null

        fun getDatabase(context: Context): CardDbDatabase {
            if (db == null)
                db = Room.databaseBuilder(
                    context.applicationContext,
                    CardDbDatabase::class.java,
                    "cards.db"
                )
                    .build()
            return db as CardDbDatabase
        }
    }

    abstract fun getCardDbDao() :CardDbDao
}