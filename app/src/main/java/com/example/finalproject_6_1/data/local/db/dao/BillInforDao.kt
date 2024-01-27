package com.example.finalproject_6_1.data.local.db.dao

import androidx.room.*
import com.example.finalproject_6_1.data.local.db.model.BillInfo

@Dao
interface BillInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBillInfo(billInfo: BillInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(billInfo: BillInfo): Int

    @Delete
    suspend fun delete(billInfo: BillInfo): Int

    @Query("SELECT * FROM BILL_INFO ORDER BY id DESC")
    fun getAllBillInfo(): ArrayList<BillInfo>



    @Query("SELECT * FROM BILL_INFO WHERE payment_type = :paymentType")
    fun getBillInfoByPaymentType(paymentType: String): BillInfo

    @Query("DELETE FROM BILL_INFO")
    suspend fun deleteBillInfo(): Int
}