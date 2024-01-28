package com.example.finalproject_6_1.Ui.bill_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject_6_1.Ui.adapter.BillListAdapter
import com.example.finalproject_6_1.Ui.setup.BillListModel
import com.example.finalproject_6_1.Ui.setup.SetupActivity
import com.example.finalproject_6_1.data.local.db.AppDatabase
import com.example.finalproject_6_1.data.local.db.`interface`.ItemBillList
import com.example.finalproject_6_1.data.local.db.model.*
import com.example.finalproject_6_1.databinding.ActivityBillListBinding

class BillListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBillListBinding
    private lateinit var listBill : ArrayList<InvoiceInfoModel>
    private lateinit var billListAdapter: BillListAdapter
    private lateinit var customerInfoList: List<CustomerInfo>
    private lateinit var billInfoList: List<BillInfo>
    private lateinit var billDetailList: List<BillDetail>
    private lateinit var qrCodeInfoList: List<QrCodeInfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        listener()
    }

    private fun initUI() {
//        customerInfoList = AppDatabase.invoke(this@BillListActivity).customerInfoDao().getAllCustomerInfo()
//        billInfoList = AppDatabase.invoke(this@BillListActivity).billInfoDao().getAllBillInfo()
//        billDetailList = AppDatabase.invoke(this@BillListActivity).billDetailDao().getAllBillDetails()
//        qrCodeInfoList = AppDatabase.invoke(this@BillListActivity).qrCodeInfoDao().getAllQrCode()
//        listBill = combineData(customerInfoList, billInfoList, billDetailList, qrCodeInfoList)
        listBill = AppDatabase(this@BillListActivity).invoiceInfoDao().getAllInvoiceInfo() as ArrayList<InvoiceInfoModel>
        billListAdapter = BillListAdapter(listBill,object :ItemBillList{
            override fun item(invoiceInfoModel: InvoiceInfoModel, status: String) {
                if (status == "Fix"){
                    fixBill(invoiceInfoModel)
                }else if (status == "Preview"){

                }else if (status == "Delete"){
                    deleteBill(invoiceInfoModel)
                }
            }
        })
        binding.apply {
            rcvBillList.setHasFixedSize(true)
            rcvBillList.layoutManager = LinearLayoutManager(this@BillListActivity,LinearLayoutManager.VERTICAL,false)
            rcvBillList.adapter = billListAdapter
        }

    }

    private fun fixBill(invoiceInfoModel: InvoiceInfoModel) {

    }
    private fun deleteBill(invoiceInfoModel: InvoiceInfoModel) {

    }


//    private fun combineData(customerInfoList: List<CustomerInfo>, billInfoList: List<BillInfo>, billDetailList: List<BillDetail>, qrCodeInfoList: List<QrCodeInfo>): ArrayList<BillListModel>  {
//        for (i in customerInfoList.indices) {
//            val customerInfo = customerInfoList[i]
//            val billInfo = billInfoList[i]
//            val billDetail = billDetailList[i]
//            val qrCodeInfo = qrCodeInfoList[i]
//
//            val billListModel = BillListModel(customerInfo, billInfo, qrCodeInfo, billDetail)
//            listBill.add(billListModel)
//        }
//        return listBill
//    }

    private fun listener() {
        binding.btnCreateTemplate.setOnClickListener {
            val intent = Intent(this, SetupActivity::class.java)
            startActivity(intent)
        }
    }
}
