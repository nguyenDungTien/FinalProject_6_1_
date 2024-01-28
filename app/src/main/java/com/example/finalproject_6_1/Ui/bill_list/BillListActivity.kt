package com.example.finalproject_6_1.Ui.bill_list

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        val intent = Intent(this@BillListActivity, SetupActivity::class.java)
        val updateData = true

        // Đính kèm đối tượng InvoiceInfoModel vào Intent
        intent.putExtra("invoiceInfoModel", invoiceInfoModel)
        intent.putExtra("updateData", true)

        startActivity(intent)
    }
    private fun deleteBill(invoiceInfoModel: InvoiceInfoModel) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Bạn có muốn xóa hóa đơn không?")
            .setMessage("Xóa ${invoiceInfoModel.typeTicket}")
            .setPositiveButton("Có") { _, _ ->
                val database = AppDatabase(this@BillListActivity)
                database.invoiceInfoDao().deleteInvoiceInfo(invoiceInfoModel)

                Toast.makeText(this@BillListActivity, "Xóa thành công", Toast.LENGTH_SHORT).show()

                if (!isDestroyed) {
                    listBill.remove(invoiceInfoModel)
                    billListAdapter.notifyItemRemoved(listBill.indexOf(invoiceInfoModel))
                }
            }
            .setNegativeButton("Không") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }


    private fun listener() {
        binding.btnCreateTemplate.setOnClickListener {
            val intent = Intent(this, SetupActivity::class.java)
            startActivity(intent)
        }
    }
}
