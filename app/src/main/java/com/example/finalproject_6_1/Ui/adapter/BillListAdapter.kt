package com.example.finalproject_6_1.Ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_6_1.Ui.setup.BillListModel
import com.example.finalproject_6_1.data.local.db.`interface`.ItemBillList
import com.example.finalproject_6_1.data.local.db.model.InvoiceInfoModel
import com.example.finalproject_6_1.databinding.ItemBillBinding

class BillListAdapter(val listBill:ArrayList<InvoiceInfoModel>, val itemBillList:ItemBillList):RecyclerView.Adapter<BillListAdapter.BillListViewHolder>() {
    inner class BillListViewHolder(val binding: ItemBillBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(invoiceInfoModel: InvoiceInfoModel){
            binding.apply {
                tvNameCompany.text = invoiceInfoModel.nameCompany
                tvNameGoods.text = invoiceInfoModel.merchandise
                tvTypeInvoice.text = invoiceInfoModel.typeTicket
                tvUnitPrice.text = invoiceInfoModel.priceUnit
                tvPreviewInvoice.setOnClickListener {
                    itemBillList.item(invoiceInfoModel,"Preview")
                }
                tvFixInvoice.setOnClickListener {
                    itemBillList.item(invoiceInfoModel,"Fix")
                }
                tvDeleteInvoice.setOnClickListener {
                    itemBillList.item(invoiceInfoModel,"Delete")
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillListViewHolder {
        val layoutÌnflater = LayoutInflater.from(parent.context)
        val binding = ItemBillBinding.inflate(layoutÌnflater,parent,false)
        return BillListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BillListViewHolder, position: Int) {
        val invoiceInfoModel = listBill[position]
        holder.bind(invoiceInfoModel)
    }

    override fun getItemCount(): Int {
        return listBill.size
    }
}