package com.example.finalproject_6_1.data.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "BILL_INFO")
data class BillInfo(

    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "group_user")
    var groupUser: String?,

//    @ColumnInfo(name = "group")
//    var group: Long?,

//    @ColumnInfo(name = "id_remote")
//    var idRemote: String,

//    @ColumnInfo(name = "printCode")
//    var printCode: Boolean?,

    @ColumnInfo(name = "name_company")
    var nameCompany: String?,

    @ColumnInfo(name = "address")
    var address: String?,

    @ColumnInfo(name = "payment_type")
    var paymentType: String?,

    @ColumnInfo(name = "money_type")
    var moneyType: String?,

    @ColumnInfo(name = "invoice_form")
    var billForm: String?,

//    @ColumnInfo(name = "invoice_form_print")
//    var originalTemplateCode: String?,

//    @ColumnInfo(name = "invoice_symbol")
//    var billSymbol: String?,
    @ColumnInfo(name = "tax_code")
    var taxCode: String?,

    @ColumnInfo(name = "date")
    var date: String?,
)