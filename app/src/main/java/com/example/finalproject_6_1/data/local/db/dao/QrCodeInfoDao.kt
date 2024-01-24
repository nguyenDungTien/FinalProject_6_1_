package com.example.finalproject_6_1.data.local.db.dao

import androidx.room.*
import com.example.finalproject_6_1.data.local.db.model.QrCodeInfo

@Dao
interface QrCodeInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(qrCodeInfo: QrCodeInfo) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(qrCodeInfo: QrCodeInfo): Int

    @Delete
    suspend fun delete(qrCodeInfo: QrCodeInfo): Int

    @Query("SELECT * FROM QR_CODE_INFO")
    fun getQrCodeWithUser(): List<QrCodeInfo>

    @Query("DELETE FROM QR_CODE_INFO")
    suspend fun deleteQrCode(): Int
}