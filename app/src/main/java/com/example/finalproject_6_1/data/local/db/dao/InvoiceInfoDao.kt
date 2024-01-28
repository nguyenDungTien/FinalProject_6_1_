package com.example.finalproject_6_1.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalproject_6_1.data.local.db.model.InvoiceInfoModel
@Dao
interface InvoiceInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoiceInfo(invoiceInfo: InvoiceInfoModel)
    @Query("SELECT * FROM INVOICE_INFO ORDER BY id DESC")
    fun getAllInvoiceInfo(): List<InvoiceInfoModel>
    @Query("SELECT COUNT(*) FROM INVOICE_INFO WHERE type_ticket = :typeTicket")
    fun isTypeTicketExists(typeTicket: String): Boolean
}