package com.shanks.medpal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shanks.medpal.models.Medicine


@Database(entities = [Medicine::class], version = 1)
abstract class MedicineDatabase : RoomDatabase() {

    abstract fun medicineDao() : MedicineDAO

    companion object{
        @Volatile
        private var instance : MedicineDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MedicineDatabase::class.java,
            "medicine"
        ).build()



    }



}