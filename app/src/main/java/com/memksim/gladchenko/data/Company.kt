package com.memksim.cursach.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
data class Company(
    @PrimaryKey(autoGenerate = true) val id: Int,
    //название
    val name: String,
    //идентификаторы вакансий
    @ColumnInfo(name = "vacanciec_ids") val vacanciesIds: String
)