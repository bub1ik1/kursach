package com.memksim.gladchenko.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("head_hunter")
data class HeadHunter(
    @PrimaryKey(autoGenerate = true) val id: Int,
    //имя
    val name: String,
    //идентификатор компании
    val companyId: Int,
    //контактный номер телефона
    @ColumnInfo("contact_phone") val contactPhone: String,
    //идентификаторы вакансий
    @ColumnInfo(name = "vacancies_ids") val vacanciesIds: String
)