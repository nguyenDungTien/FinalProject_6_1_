package com.example.finalproject_6_1.data.local.db.dao

import androidx.room.*
import com.example.finalproject_6_1.data.local.db.model.BillDetail

@Dao
interface BillDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBillDetail(billDetail: BillDetail)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(billDetail: BillDetail): Int

    @Delete
    suspend fun delete(billDetail: BillDetail): Int

    @Query("SELECT * FROM BILL_DETAIL")
    fun getAllBillDetails(): List<BillDetail>

    @Query("SELECT * FROM BILL_DETAIL ORDER BY id DESC")
    fun getAllBillDetail(group_user: String): ArrayList<BillDetail>

//    @Query("SELECT * FROM BILL_DETAIL WHERE group_user = :group_user")
//    fun getBillDetailByPercentGroupUser(group_user: String): List<BillDetail>
//
//    @Query("SELECT * FROM BILL_DETAIL WHERE type_ticket = :typeTicket AND group_user = :groupUser")
//    suspend fun getBillDetailByTypeTicket(typeTicket: String, groupUser: String): BillDetail?
//
//    @Query("SELECT * FROM BILL_DETAIL WHERE group_user = :groupUser")
//    fun getBillDetailByTypeTicketUpper(groupUser: String): List<BillDetail>?

    @Query("UPDATE BILL_DETAIL SET total_money_after_tax = :totalMoneyAfterTax WHERE id = :id")
    fun updatetotalMoneyAfterTaxBillDetailIById(id: Long, totalMoneyAfterTax: String): Int

    @Query("UPDATE BILL_DETAIL SET tax_money = :taxMoney WHERE id = :id")
    fun updateTaxMoneyBillDetailIById(id: Long, taxMoney: String): Int

    @Query("UPDATE BILL_DETAIL SET price_unit = :priceUnit WHERE id = :id")
    fun updatePriceUnitBillDetailIById(id: Long, priceUnit: String): Int

    @Query("DELETE FROM BILL_DETAIL")
    suspend fun deleteBillDetail(): Int
}