package com.example.finalproject_6_1.Ui.userfragment.accountfragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.finalproject_6_1.BuildConfig
import com.example.finalproject_6_1.R
import com.example.finalproject_6_1.Ui.Login.LoginActivity
import com.example.finalproject_6_1.databinding.FragmentAccountBinding
import com.example.finalproject_6_1.databinding.FragmentAccountUserBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class AccountUserFragment : Fragment() {
    private lateinit var binding: FragmentAccountUserBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountUserBinding.inflate(inflater, container, false)
        FirebaseApp.initializeApp(requireContext())
        auth = FirebaseAuth.getInstance()
        ui()
        listener()
        return binding.root
    }

    private fun ui() {
        val versionCode: Int = BuildConfig.VERSION_CODE
        val versionName: String = BuildConfig.VERSION_NAME
        binding.tvVersion.text="Phiên bản: $versionName ($versionCode)"
        val user = FirebaseAuth.getInstance().currentUser ?: return
        binding.tvEmailLogin.text = "Email đăng nhập: ${user.email}"
    }

    private fun listener() {
        binding.btnLogoutUser.setOnClickListener {
            showLogoutConfirmationDialog()
        }
        binding.btnChangePasswordUser.setOnClickListener {
            onClickChangePassword()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        alertDialogBuilder.setTitle("Xác nhận đăng xuất")
        alertDialogBuilder.setMessage("Bạn có chắc chắn muốn đăng xuất không?")

        alertDialogBuilder.setPositiveButton("Có") { _, _ ->
            // Người dùng đã xác nhận đăng xuất
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }

        alertDialogBuilder.setNegativeButton("Không") { dialog, _ ->
            // Người dùng đã chọn không đăng xuất, đóng dialog
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun onClickChangePassword() {
        val builder= AlertDialog.Builder(requireContext())
        val dialogView=layoutInflater.inflate(R.layout.dialog_forgot_password,null)
        val editText=dialogView.findViewById<EditText>(R.id.edt_email)
        builder.setView(dialogView)
        val dialog: AlertDialog =builder.create()
        dialogView.findViewById<Button>(R.id.btn_send_email).setOnClickListener {
            val userEmail=editText.text.toString().trim()
            if (TextUtils.isEmpty(userEmail)&&!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                Toast.makeText(requireContext(), "Nhập địa chỉ Email.", Toast.LENGTH_SHORT).show()
            }
            auth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Đã gửi email đặt lại mật khẩu. Kiểm tra hộp thư điện tử của bạn.", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(requireContext(), "Không gửi được email đặt lại mật khẩu. Vui lòng kiểm tra địa chỉ email của bạn.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        dialogView.findViewById<Button>(R.id.btn_close_dialog).setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }


}