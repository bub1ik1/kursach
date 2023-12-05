package com.memksim.gladchenko

import android.app.Application
import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import androidx.room.RoomDatabase
import com.memksim.gladchenko.data.JobSeeker
import com.memksim.gladchenko.data.Database
import com.memksim.gladchenko.data.HhDao
import javax.inject.Inject
import javax.inject.Singleton

class DatabaseManager @Inject constructor(
    private val dao: HhDao
) {

    suspend fun addJobSeeker(jobSeeker: JobSeeker) = dao.insertJobSeeker(jobSeeker)

    suspend fun getJobSeekers() = dao.fetchJobSeekers()

    suspend fun getJobSeekerById(id: Int) = dao.fetchJobSeekerById(id)

    suspend fun saveNewJobSeeker(jobSeeker: JobSeeker) = dao.insertJobSeeker(jobSeeker)

}