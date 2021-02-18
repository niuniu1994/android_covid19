package com.example.covid19.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.covid19.dao.CountryDao
import com.example.covid19.entity.Country

/**
 * Database
 */
@Database(entities = [Country::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    /**
     * Get the singleton database
     */
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDataBase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "country_database"
                        ).build()
                        return INSTANCE as AppDatabase
                    }
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}
