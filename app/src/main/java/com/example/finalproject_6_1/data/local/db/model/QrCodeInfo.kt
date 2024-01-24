package com.example.finalproject_6_1.data.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "QR_CODE_INFO")
data class QrCodeInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "group_user")
    var groupUser: String?,

    @ColumnInfo(name = "group")
    var group: Long?,

    @ColumnInfo(name = "is_create")
    var isCreate: Boolean?,

    @ColumnInfo(name = "total_qrcode")
    var totalQrcode: String?,

    @ColumnInfo(name = "start_date_qrcode")
    var startDateQrcode: String?,

    @ColumnInfo(name = "end_date_qrcode")
    var endDateQrcode: String?,
    @Ignore
    var isUpdateQrInfo:Boolean =  false
)
