package com.shanks.medpal.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shanks.medpal.models.Numbers

@Dao
interface NumbersDAO  {

    @Insert
    suspend fun insertNumber(number: Numbers)

    @Delete
    suspend fun deleteMedicine(number: Numbers)

    @Update
    suspend fun updateMedicine(number: Numbers)

    @Query("Delete from numbers")
    suspend fun deleteEveryThing()

    @Query("SELECT * FROM numbers")
    fun getNumbers() : LiveData<List<Numbers>>

    @Query("Select num from numbers")
    fun getNumList() : List<String>

}