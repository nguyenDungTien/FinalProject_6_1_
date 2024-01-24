package com.example.finalproject_6_1.Ui.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject_6_1.R
import com.example.finalproject_6_1.databinding.BottomSheetBinding
import com.example.finalproject_6_1.databinding.FragmentAccountBinding
import com.example.finalproject_6_1.databinding.FragmentBottomSheetBinding
import com.example.finalproject_6_1.databinding.FragmentSetupInfoBinding

class BottomSheetFragment : Fragment() {
    private lateinit var binding: BottomSheetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetBinding.inflate(inflater, container, false)



        return binding.root
    }


}