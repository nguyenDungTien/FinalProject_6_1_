package com.example.finalproject_6_1.Ui.userfragment.scarQRfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject_6_1.databinding.FragmentScarBinding
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ScarFragment : Fragment() {
    private lateinit var binding: FragmentScarBinding
    private lateinit var mScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScarBinding.inflate(inflater, container, false)


        return binding.root
    }


}