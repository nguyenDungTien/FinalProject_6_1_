package com.example.finalproject_6_1.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalproject_6_1.data.local.db.dao.BillDetailDao
import com.example.finalproject_6_1.data.local.db.dao.BillInfoDao
import com.example.finalproject_6_1.data.local.db.dao.CustomerInfoDao
import com.example.finalproject_6_1.data.local.db.dao.QrCodeInfoDao
import com.example.finalproject_6_1.data.local.db.model.BillInfo

@Database(entities = [BillInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
        }
    }

    abstract fun billInfoDao(): BillInfoDao
    abstract fun customerInfoDao(): CustomerInfoDao
    abstract fun billDetailDao(): BillDetailDao
    abstract fun qrCodeInfoDao(): QrCodeInfoDao



}