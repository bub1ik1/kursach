package com.memksim.gladchenko

import android.content.Context
import androidx.room.Room
import com.memksim.gladchenko.data.Database
import com.memksim.gladchenko.data.HhDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideDao(@ApplicationContext context: Context): HhDao = Room.databaseBuilder(
        context,
        Database::class.java, "head_hunting_database"
    ).build().getDao()

    /*@Provides
    fun provideManager(dao: HhDao): DatabaseManager = DatabaseManager(dao)*/

}