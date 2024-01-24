package com.example.finalproject_6_1.Ui.adminfragment.statisticalfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject_6_1.Ui.adapter.StatisticalAdapter
import com.example.finalproject_6_1.Ui.callback.OnDeleteItemClickListener
import com.example.finalproject_6_1.databinding.FragmentStatisticalBinding
import com.google.firebase.firestore.FirebaseFirestore

class StatisticalFragment : Fragment() {

    private lateinit var binding: FragmentStatisticalBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var statisticalAdapter: StatisticalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticalBinding.inflate(inflater, container, false)
        setupRecyclerView()
        fetchDataFromFirebase()
        return binding.root
    }

    private fun setupRecyclerView() {
        statisticalAdapter = StatisticalAdapter(requireContext(), mutableListOf(), object : OnDeleteItemClickListener {
            override fun onDeleteItemClick(accountModel: AccountModel) {
                showDeleteConfirmationDialog(accountModel)
            }
        })

        binding.rcvAccounts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = statisticalAdapter
        }
    }

    private fun fetchDataFromFirebase() {
        val collectionRef = db.collection("Users")

        collectionRef.get()
            .addOnSuccessListener { result ->
                val dataList = mutableListOf<AccountModel>()

                for (document in result) {
                    val data = document.data
                    val name = data["Name"].toString()
                    val email = data["Email"].toString()
                    val phone = data["Phone"].toString()
                    val typeAccount: String = when {
                        data.containsKey("isAdmin") -> data["isAdmin"].toString()
                        data.containsKey("isUser") -> data["isUser"].toString()
                        else -> "DefaultType"
                    }

                    val accountModel = AccountModel(email, name, phone, typeAccount)
                    dataList.add(accountModel)
                }

                updateUI(dataList)
            }
            .addOnFailureListener { exception ->
                Log.e("StatisticalFragment", "Error getting documents: $exception")
            }
    }

    private fun updateUI(data: MutableList<AccountModel>) {
        statisticalAdapter.setData(data)
    }

    private fun showDeleteConfirmationDialog(accountModel: AccountModel) {
        AlertDialog.Builder(requireContext())
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa ${accountModel.name} không?")
            .setPositiveButton("Xóa") { _, _ ->
                deleteUserFromFirestore(accountModel)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun deleteUserFromFirestore(accountModel: AccountModel) {
        db.collection("Users").whereEqualTo("Email", accountModel.email).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        // Delete the document
                        db.collection("Users").document(document.id)
                            .delete()
                            .addOnSuccessListener {
                                // Document successfully deleted
                                // Now you can update your UI or take other actions
                                statisticalAdapter.removeData(accountModel)
                            }
                            .addOnFailureListener { exception ->
                            }
                    }
                } else {
                }
            }
    }
}