package com.yeditepe.minduplast.mindup.di

import android.app.Application
import androidx.room.Room
import androidx.test.espresso.core.internal.deps.dagger.Module
import androidx.test.espresso.core.internal.deps.dagger.Provides
import com.yeditepe.minduplast.data.local.MoodDatabase
import com.yeditepe.minduplast.data.local.MoodDao
import com.yeditepe.minduplast.data.repository.MoodRepositoryImpl
import com.yeditepe.minduplast.domain.repository.MoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.text.Typography.dagger

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