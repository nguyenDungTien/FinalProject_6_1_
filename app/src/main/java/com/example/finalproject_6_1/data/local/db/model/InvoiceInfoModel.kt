package com.example.finalproject_6_1.data.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "INVOICE_INFO")
data class InvoiceInfoModel(
    @ColumnInfo(name = "name_company")
    var nameCompany: String?,

    @ColumnInfo(name = "address_company")
    var addressCompany: String?,

    @ColumnInfo(name = "payment_type")
    var paymentType: String?,

    @ColumnInfo(name = "money_type")
    var moneyType: String?,
    @ColumnInfo(name = "date")
    var date: String?,
    //

    @ColumnInfo(name = "buy_not_receive")
    var buyNotReceiver: Boolean?,

    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "address")
    var address: String?,

    @ColumnInfo(name = "legal_name")
    var legalName: String?,
    @ColumnInfo(name = "phone_number")
    var phoneNumber: String?,

    @ColumnInfo(name = "email")
    var email: String?,
    //

    @ColumnInfo(name = "property")
    var property: String?,

    @ColumnInfo(name = "merchandise")
    var merchandise: String?,
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
    var quantityPerPrint: String?,
    //

    @ColumnInfo(name = "is_create")
    var isCreate: Boolean?,

    @ColumnInfo(name = "total_qrcode")
    var totalQrcode: String?,

    @ColumnInfo(name = "start_date_qrcode")
    var startDateQrcode: String?,

    @ColumnInfo(name = "end_date_qrcode")
    var endDateQrcode: String?,

): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
