package com.example.finalproject_6_1.Ui.userfragment.releasedfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject_6_1.Ui.bill_list.BillListActivity
import com.example.finalproject_6_1.databinding.FragmentReleasedBinding


class ReleasedFragment : Fragment() {
    private lateinit var binding: FragmentReleasedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReleasedBinding.inflate(inflater, container, false)
        listenner()
        return binding.root
    }

    private fun listenner() {
        binding.ivListBill.setOnClickListener {
            val intent = Intent(activity, BillListActivity::class.java)
            startActivity(intent)
        }
    }


}