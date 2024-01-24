package com.example.finalproject_6_1.Ui.bill_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalproject_6_1.Ui.setup.SetupActivity
import com.example.finalproject_6_1.databinding.ActivityBillListBinding

class BillListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBillListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listener()
    }

    private fun listener() {
        binding.btnCreateTemplate.setOnClickListener {
            val intent = Intent(this, SetupActivity::class.java)
            startActivity(intent)
        }
    }
}