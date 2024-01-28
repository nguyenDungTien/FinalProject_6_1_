package com.example.finalproject_6_1.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalproject_6_1.data.local.db.dao.*
import com.example.finalproject_6_1.data.local.db.model.*

@Database(entities = [InvoiceInfoModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val LOCK =Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).allowMainThreadQueries().build()
        }
    }

//    abstract fun billInfoDao(): BillInfoDao
//    abstract fun customerInfoDao(): CustomerInfoDao
//    abstract fun billDetailDao(): BillDetailDao
//    abstract fun qrCodeInfoDao(): QrCodeInfoDao
    abstract fun invoiceInfoDao(): InvoiceInfoDao



}