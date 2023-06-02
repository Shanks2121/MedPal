package com.shanks.medpal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shanks.medpal.models.Numbers

@Database(entities = [Numbers::class], version = 1)
abstract class NumbersDatabase : RoomDatabase() {

    abstract fun numbersDao() : NumbersDAO

    companion object{
        @Volatile
        private var instance : NumbersDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NumbersDatabase::class.java,
            "numbers"
        ).build()



    }



}