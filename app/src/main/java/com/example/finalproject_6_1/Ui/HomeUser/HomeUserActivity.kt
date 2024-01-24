package com.example.finalproject_6_1.Ui.HomeUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.finalproject_6_1.R
import com.example.finalproject_6_1.Ui.adminfragment.accountfragment.AccountFragment
import com.example.finalproject_6_1.Ui.adminfragment.reportfragment.ReportFragment
import com.example.finalproject_6_1.Ui.adminfragment.statisticalfragment.StatisticalFragment
import com.example.finalproject_6_1.Ui.userfragment.accountfragment.AccountUserFragment
import com.example.finalproject_6_1.Ui.userfragment.historyfragment.HistoryFragment
import com.example.finalproject_6_1.Ui.userfragment.releasedfragment.ReleasedFragment
import com.example.finalproject_6_1.Ui.userfragment.reportfragment.ReportUserFragment
import com.example.finalproject_6_1.Ui.userfragment.scarQRfragment.ScarFragment
import com.example.finalproject_6_1.databinding.ActivityHomeUserBinding

class HomeUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeUserBinding
    private var selectedItemId: Int = R.id.released
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            selectedItemId = savedInstanceState.getInt("selectedItemId", R.id.released)
        }
        binding.bottomNavigationView.selectedItemId = R.id.released
        when (selectedItemId) {
            R.id.history -> replaceFragment(HistoryFragment())
            R.id.released -> replaceFragment(ReleasedFragment())
            R.id.scanQR -> replaceFragment(ScarFragment())
            R.id.report -> replaceFragment(ReportUserFragment())
            R.id.account -> replaceFragment(AccountUserFragment())
        }

//        supportFragmentManager.beginTransaction().replace(R.id.frame_layout_user, ReleasedFragment()).commit()
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.history -> {
                    selectedItemId = R.id.history
                    replaceFragment(HistoryFragment())
                    true
                }
                R.id.released -> {
                    selectedItemId = R.id.released
                    replaceFragment(ReleasedFragment())
                    true
                }
                R.id.scanQR -> {
                    selectedItemId = R.id.scanQR
                    replaceFragment(ScarFragment())
                    true
                }
                R.id.report -> {
                    selectedItemId = R.id.report
                    replaceFragment(ReportUserFragment())
                    true
                }
                R.id.account -> {
                    selectedItemId = R.id.account
                    replaceFragment(AccountUserFragment())
                    true
                }
                else -> false
            }
        }

    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout_user, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedItemId", selectedItemId)
    }
}