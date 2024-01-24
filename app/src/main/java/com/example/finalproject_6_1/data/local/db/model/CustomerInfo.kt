package com.example.finalproject_6_1.data.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "CUSTOMER_INFO")
data class CustomerInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    @ColumnInfo(name = "group_user")
    var groupUser: String?,

    @ColumnInfo(name = "buy_not_receive")
    var buyNotReciver: Boolean?,

    @ColumnInfo(name = "name")
    var name: String?,
//
//    @ColumnInfo(name = "buyer_code")
//    var buyerCode: String?,

    @ColumnInfo(name = "buyer_birthDay")
    var buyerBirthDay: String?,

    @ColumnInfo(name = "address")
    var address: String?,

    @ColumnInfo(name = "legal_name")
    var legalName: String?,

    @ColumnInfo(name = "tax_code")
    var taxCode: String?,

    @ColumnInfo(name = "postal_code")
    var postalCode: String?,

    @ColumnInfo(name = "phone_number")
    var phoneNumber: String?,

    @ColumnInfo(name = "email")
    var email: String?,

//    @ColumnInfo(name = "type_paper")
//    var typePaper: String?,

//    @ColumnInfo(name = "id_no")
//    var idNo: String?,

//    @ColumnInfo(name = "bank_name")
//    var bankName: String?,
//
//    @ColumnInfo(name = "bank_account")
//    var bankAccount: String?
)
