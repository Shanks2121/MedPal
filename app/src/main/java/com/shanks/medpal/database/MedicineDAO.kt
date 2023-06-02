package com.shanks.medpal.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shanks.medpal.models.Medicine

@Dao
interface MedicineDAO  {

    @Insert
    suspend fun insertMedicine(medicine: Medicine)

    @Delete
    suspend fun deleteMedicine(medicine: Medicine)

    @Update
    suspend fun updateMedicine(medicine: Medicine)

    @Query("Delete from medicine")
    suspend fun deleteEveryThing()

    @Query("SELECT * FROM medicine")
    fun getMedicine() : LiveData<List<Medicine>>

}