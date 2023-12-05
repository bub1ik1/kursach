package com.memksim.gladchenko.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("job_seeker")
data class JobSeeker(
    @PrimaryKey(autoGenerate = true) val id: Int,
    //фио
    @ColumnInfo(name = "full_name") val fullName: String,
    //возраст
    val age: Int,
    //навыки
    val skills: String,
    //Желаемая зп
    val wantedSalary: Double,
    //идентификаторы понравившихся вакансий
    @ColumnInfo(name = "vacancies_ids") val vacanciesIds: String
)