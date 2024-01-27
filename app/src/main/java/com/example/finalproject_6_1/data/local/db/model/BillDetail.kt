package com.example.finalproject_6_1.data.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "BILL_DETAIL")

data class BillDetail(


//    @ColumnInfo(name = "group_user")
//    var groupUser: String?,
//
//    @ColumnInfo(name = "group")
//    var group: Long?,

    @ColumnInfo(name = "property")
    var property: String?,

    @ColumnInfo(name = "merchandise")
    var merchandise: String?,

//    @ColumnInfo(name = "code_merchandise")
//    var codeMerchandise: String?,

    @ColumnInfo(name = "type_ticket")
    var typeTicket: String?,

    @ColumnInfo(name = "calc_unit")
    var calcUnit: String?,

    @ColumnInfo(name = "amount")
    var amount: String?,

    @ColumnInfo(name = "price_unit")
    var priceUnit: String?,

    @ColumnInfo(name = "tax")
    var tax: String?,

    @ColumnInfo(name = "tax_money")
    var taxMoney: String?,

    @ColumnInfo(name = "total_money_after_tax")
    var totalMoneyAfterTax: String?,

    @ColumnInfo(name = "quantity_per_print")
    var quantityPerPrint: String?

//    @ColumnInfo(name = "has_code")
//    var hasCode: Int = 0,

//    @Ignore
//    var isUpdateDetailBill :Boolean = false
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
