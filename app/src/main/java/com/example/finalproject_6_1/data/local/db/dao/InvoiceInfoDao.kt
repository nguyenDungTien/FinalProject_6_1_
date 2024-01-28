package com.example.finalproject_6_1.data.local.db.dao

import androidx.room.*
import com.example.finalproject_6_1.data.local.db.model.InvoiceInfoModel
@Dao
interface InvoiceInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoiceInfo(invoiceInfo: InvoiceInfoModel)
    @Update
    fun updateInvoiceInfo(invoiceInfo: InvoiceInfoModel)
    @Delete
    fun deleteInvoiceInfo(invoiceInfo: InvoiceInfoModel)
    @Query("SELECT * FROM INVOICE_INFO ORDER BY id DESC")
    fun getAllInvoiceInfo(): List<InvoiceInfoModel>
    @Query("SELECT COUNT(*) FROM INVOICE_INFO WHERE type_ticket = :typeTicket")
    fun isTypeTicketExists(typeTicket: String): Boolean
}