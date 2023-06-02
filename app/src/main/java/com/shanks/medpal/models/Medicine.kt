package com.shanks.medpal.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicine")
data class Medicine(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val medicineName : String,
    val medicineType : String,
    val dayFormat : String

)