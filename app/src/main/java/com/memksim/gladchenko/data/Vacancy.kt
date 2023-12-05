package com.memksim.gladchenko.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy")
class Vacancy(
    @PrimaryKey(autoGenerate = true) val id: Int,
    //название
    val title: String,
    //требуемые навыки
    val skills: String,
    //зп
    val salary: Double,
    //идентификатор рекрутера
    val hunterId: Int,
    //идентификатор компании
    val companyId: Int,
)