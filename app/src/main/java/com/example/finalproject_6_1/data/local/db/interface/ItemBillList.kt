package com.example.finalproject_6_1.data.local.db.`interface`

import com.example.finalproject_6_1.Ui.setup.BillListModel
import com.example.finalproject_6_1.data.local.db.model.InvoiceInfoModel

interface ItemBillList {
    fun item(invoiceInfoModel: InvoiceInfoModel,status:String)
}