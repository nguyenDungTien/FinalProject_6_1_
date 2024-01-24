package com.example.finalproject_6_1.Ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_6_1.databinding.BottomSheetBinding
import com.example.finalproject_6_1.databinding.ItemBotttomSheetBinding

class ItemDialogBottomSheetAdapter(
    private var listItemBottomSheet: ArrayList<String>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(selectedItem: String)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemBotttomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding,listener,listItemBottomSheet)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val currentItem = listItemBottomSheet[position]
            holder.tvItemBottomSheet.text = currentItem
        }
    }

    override fun getItemCount(): Int {
        return listItemBottomSheet.size
    }

    class ViewHolder(private val binding: ItemBotttomSheetBinding, private val listener: OnItemClickListener,private val listItemBottomSheet: ArrayList<String>) :
        RecyclerView.ViewHolder(binding.root) {
        val tvItemBottomSheet: TextView = binding.tvItemBottomSheet
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedItem = listItemBottomSheet[position]
                    listener.onItemClick(selectedItem)
                }
            }
        }

    }
}