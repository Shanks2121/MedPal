package com.shanks.medpal.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numbers")
data class Numbers(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val num : String

)
