package com.cumaliguzel.mindup.di

import android.app.Application
import androidx.room.Room
import com.cumaliguzel.mindup.data.local.MoodDatabase
import com.cumaliguzel.mindup.data.local.MoodDao
import com.cumaliguzel.mindup.data.repository.MoodRepositoryImpl
import com.cumaliguzel.mindup.domain.repository.MoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoodDatabase(app: Application): MoodDatabase {
        return Room.databaseBuilder(
            app,
            MoodDatabase::class.java,
            MoodDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoodDao(database: MoodDatabase): MoodDao {
        return database.moodDao()
    }

    @Provides
    @Singleton
    fun provideMoodRepository(dao: MoodDao): MoodRepository {
        return MoodRepositoryImpl(dao)
    }
} 