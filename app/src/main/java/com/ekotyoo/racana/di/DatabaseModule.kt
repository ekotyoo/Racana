package com.ekotyoo.racana.di

import android.content.Context
import androidx.room.Room
import com.ekotyoo.racana.data.datasource.local.database.TourPlanDao
import com.ekotyoo.racana.data.datasource.local.database.TourPlanDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesTourPlanDatabase(@ApplicationContext context: Context): TourPlanDatabase {
        return Room.databaseBuilder(
            context,
            TourPlanDatabase::class.java,
            "tourplan.db"
        ).build()
    }

    @Provides
    fun providesTourPlanDao(database: TourPlanDatabase): TourPlanDao = database.tourPlanDao()
}