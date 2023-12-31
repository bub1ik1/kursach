package com.memksim.gladchenko.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Company::class, HeadHunter::class, JobSeeker::class, Vacancy::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun getDao(): HhDao
}