package com.example.finalproject_6_1.Ui.setup

import com.example.finalproject_6_1.data.local.db.model.BillDetail
import com.example.finalproject_6_1.data.local.db.model.BillInfo
import com.example.finalproject_6_1.data.local.db.model.CustomerInfo
import com.example.finalproject_6_1.data.local.db.model.QrCodeInfo

data class BillListModel(var customerInfo: CustomerInfo?,
                         var billInfor: BillInfo?,
                         var qrCodeInfo: QrCodeInfo?,
                         var billDetails: BillDetail?)
