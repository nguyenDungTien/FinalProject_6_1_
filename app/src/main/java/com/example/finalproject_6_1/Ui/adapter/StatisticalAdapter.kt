package com.example.finalproject_6_1.Ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_6_1.Ui.adminfragment.statisticalfragment.AccountModel
import com.example.finalproject_6_1.Ui.callback.OnDeleteItemClickListener
import com.example.finalproject_6_1.databinding.ItemStatisticalAccountBinding

class StatisticalAdapter(
    private val context: Context,
    private var dataList: MutableList<AccountModel>,
    private val onDeleteItemClickListener: OnDeleteItemClickListener
) : RecyclerView.Adapter<StatisticalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemStatisticalAccountBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val accountModel = dataList[position]
        holder.bind(accountModel, onDeleteItemClickListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(private val binding: ItemStatisticalAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(accountModel: AccountModel, onDeleteItemClickListener: OnDeleteItemClickListener) {
            with(binding) {
                tvEmailAccount.text = "Email: ${accountModel.email}"
                tvNameAccount.text = "Họ và tên: ${accountModel.name}"
                tvPhoneAccount.text = "Số điện thoại: ${accountModel.phone}"

                val accountTypeText = when (accountModel.typeAccount) {
                    "1" -> "Loại tài khoản: Quản lí"
                    "2" -> "Loại tài khoản: Nhân viên"
                    else -> "Loại tài khoản: Unknown"
                }
                tvTypeAccount.text = accountTypeText

                ivDelete.setOnClickListener {
                    onDeleteItemClickListener.onDeleteItemClick(accountModel)
                }
            }
        }
    }

    fun setData(newData: MutableList<AccountModel>) {
        dataList = newData
        notifyDataSetChanged()
    }

    fun removeData(accountModel: AccountModel) {
        val position = dataList.indexOf(accountModel)
        if (position != -1) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}

