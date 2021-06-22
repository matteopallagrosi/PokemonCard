package it.fasm.pokemoncard.dbManager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CardDb::class], version = 1, exportSchema = true)
abstract class CardDbDatabase : RoomDatabase (){
/*
    abstract fun cardDbDao() :CardDbDao

    companion object {
        @Volatile
        private var INSTANCE: CardDbDatabase? = null

        fun getDatabase(context: Context): CardDbDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CardDbDatabase::class.java,
                        "card_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

 */

    companion object {
        private var db: CardDbDatabase? = null // Singleton
        fun getDatabase(context: Context): CardDbDatabase {
            if (db == null)
                db = Room.databaseBuilder(
                    context.applicationContext,
                    CardDbDatabase::class.java,
                    "proverbi.db"
                )

                    .build()
            return db as CardDbDatabase
        }
    }

    abstract fun cardDbDao() :CardDbDao
}