package com.example.finalproject_6_1.data.local.db.dao

import androidx.room.*
import com.example.finalproject_6_1.data.local.db.model.CustomerInfo

@Dao
interface CustomerInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomerInfo(customerInfo: CustomerInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(customerInfo: CustomerInfo): Int

    @Delete
    suspend fun delete(customerInfo: CustomerInfo): Int

    @Query("SELECT * FROM CUSTOMER_INFO ORDER BY id DESC")
    fun getAllCustomerInfo(): ArrayList<CustomerInfo>

    @Query("SELECT * FROM CUSTOMER_INFO WHERE name = :name")
    fun getCustomerInfoByName(name: String): CustomerInfo

    @Query("UPDATE CUSTOMER_INFO SET name = :name WHERE id = :id")
    fun updateNameById(id: Long, name: String): Int

    @Query("UPDATE CUSTOMER_INFO SET legal_name = :legalName WHERE id = :id")
    fun updateLegalNameById(id: Long, legalName: String): Int

    @Query("UPDATE CUSTOMER_INFO SET address = :address WHERE id = :id")
    fun updateAddressById(id: Long, address: String): Int

    @Query("DELETE FROM CUSTOMER_INFO")
    suspend fun deleteCustomerInfo(): Int
}