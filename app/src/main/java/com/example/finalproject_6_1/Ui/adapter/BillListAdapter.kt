package com.example.finalproject_6_1.Ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_6_1.Ui.setup.BillListModel
import com.example.finalproject_6_1.data.local.db.`interface`.ItemBillList
import com.example.finalproject_6_1.databinding.ItemBillBinding

class BillListAdapter(val listBill:ArrayList<BillListModel>, val itemBillList:ItemBillList):RecyclerView.Adapter<BillListAdapter.BillListViewHolder>() {
    inner class BillListViewHolder(val binding: ItemBillBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(billListModel: BillListModel){
            binding.apply {
                tvNameCompany.text = billListModel.billInfor?.nameCompany
                tvNameGoods.text = billListModel.billDetails?.merchandise
                tvTypeInvoice.text = billListModel.billDetails?.typeTicket
                tvUnitPrice.text = billListModel.billDetails?.priceUnit
                tvPreviewInvoice.setOnClickListener {
                    itemBillList.item(billListModel,"Preview")
                }
                tvFixInvoice.setOnClickListener {
                    itemBillList.item(billListModel,"Fix")
                }
                tvDeleteInvoice.setOnClickListener {
                    itemBillList.item(billListModel,"Delete")
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
        val billListModel = listBill[position]
        holder.bind(billListModel)
    }

    override fun getItemCount(): Int {
        return listBill.size
    }
}