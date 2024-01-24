package com.example.finalproject_6_1.Ui.HomeAdminActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.finalproject_6_1.R
import com.example.finalproject_6_1.Ui.adminfragment.accountfragment.AccountFragment
import com.example.finalproject_6_1.Ui.adminfragment.reportfragment.ReportFragment
import com.example.finalproject_6_1.Ui.adminfragment.statisticalfragment.StatisticalFragment
import com.example.finalproject_6_1.databinding.ActivityHomeAdminBinding

class HomeAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeAdminBinding
    private var selectedItemId: Int = R.id.statistical
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            selectedItemId = savedInstanceState.getInt("selectedItemId", R.id.statistical)
        }
        val receivedData = intent.getBooleanExtra("keyBack",false)
        if (receivedData){
            binding.bottomNavigationView.selectedItemId = R.id.account
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout_admin,AccountFragment()).commit()
        }
        binding.bottomNavigationView.selectedItemId = selectedItemId
        when (selectedItemId) {
            R.id.statistical -> replaceFragment(StatisticalFragment())
            R.id.report -> replaceFragment(ReportFragment())
            R.id.account -> replaceFragment(AccountFragment())
        }
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.statistical -> {
                    selectedItemId = R.id.statistical
                    replaceFragment(StatisticalFragment())
                    true
                }
                R.id.report -> {
                    selectedItemId = R.id.report
                    replaceFragment(ReportFragment())
                    true
                }
                R.id.account -> {
                    selectedItemId = R.id.account
                    replaceFragment(AccountFragment())
                    true
                }
                else -> false
            }
        }
    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout_admin, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedItemId", selectedItemId)
    }
}