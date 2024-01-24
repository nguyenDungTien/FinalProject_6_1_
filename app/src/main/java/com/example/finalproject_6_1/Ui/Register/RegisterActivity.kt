package com.example.finalproject_6_1.Ui.Register

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.finalproject_6_1.Ui.HomeAdminActivity.HomeAdminActivity
import com.example.finalproject_6_1.Ui.HomeUser.HomeUserActivity
import com.example.finalproject_6_1.Ui.adminfragment.accountfragment.AccountFragment
import com.example.finalproject_6_1.Ui.userfragment.accountfragment.AccountUserFragment
import com.example.finalproject_6_1.databinding.ActivityRegisterBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog
    var valid: Boolean = true
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var binding: ActivityRegisterBinding
    private val passwordRegex = "^[a-zA-Z0-9]{6,}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this@RegisterActivity)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        progressDialog = ProgressDialog(this)

        initListener()

    }
    private fun checkField(textField: EditText): Boolean {
        if (textField.text.toString().isEmpty()) {
            textField.error = "Chưa nhật thông tin"
            valid = false
        } else {
            valid = true
        }

        return valid
    }
    private fun isPasswordValid(password: String): Boolean {
        return password.matches(passwordRegex.toRegex())
    }
    private fun initListener() {
        binding.btnRegister.setOnClickListener {
            onClickSignIn()
        }
        binding.chkadmin.setOnCheckedChangeListener { _, isChecked ->
            // Handle the checked state change here
            if (isChecked) {
                binding.chkuser.isChecked = false
            }
        }
        binding.chkuser.setOnCheckedChangeListener { _, isChecked ->
            // Handle the checked state change here
            if (isChecked) {
                binding.chkadmin.isChecked = false
            }
        }
        binding.ivBtnBack.setOnClickListener {
            val intent = Intent(this, HomeAdminActivity::class.java)
            intent.putExtra("keyBack", true)
            startActivity(intent)
            finishAffinity()
        }
    }
    private fun onClickSignIn() {
        checkField(binding.edtNameReg)
        checkField(binding.edtEmailReg)
        checkField(binding.edtPhoneReg)
        checkField(binding.edtPasswordReg)
        checkField(binding.edtConfirmPasswordReg)
        val password = binding.edtPasswordReg.text.toString()
        val confirmPassword = binding.edtConfirmPasswordReg.text.toString()
        if (!isPasswordValid(password)) {
            binding.edtPasswordReg.error = "Password does not meet the criteria"
            return
        }

        if (password != confirmPassword) {
            // Passwords do not match, show an error
            binding.edtPasswordReg.error = "Passwords do not match"
            binding.edtConfirmPasswordReg.error = "Passwords do not match"
            return
        }

        if (!(binding.chkadmin.isChecked || binding.chkuser.isChecked)) {
            Toast.makeText(this, "Select the account type", Toast.LENGTH_LONG).show()
        }
        if (valid) {
            auth.createUserWithEmailAndPassword(binding.edtEmailReg.text.toString(), binding.edtPasswordReg.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        val df: DocumentReference = fStore.collection("Users").document(user?.uid.toString())
                        val userInfo: MutableMap<String, Any> = HashMap()
                        userInfo["Name"] = binding.edtNameReg.text.toString()
                        userInfo["Email"] = binding.edtEmailReg.text.toString()
                        userInfo["Phone"] = binding.edtPhoneReg.text.toString()
                        if (binding.chkadmin.isChecked) {
                            userInfo["isAdmin"] = "1"
                            startActivity(Intent(applicationContext, HomeAdminActivity::class.java))
                            finish()
                        }
                        if (binding.chkuser.isChecked) {
                            userInfo["isUser"] = "2"
                            startActivity(Intent(applicationContext, HomeUserActivity::class.java))
                            finish()
                        }

                        df.set(userInfo)
                        Toast.makeText(this, "Authentication success.", Toast.LENGTH_SHORT,).show()


                    } else {
                        if (task.exception?.message == "The email address is already in use by another account."){
                            Toast.makeText(this, "The email address is already in use by another account.", Toast.LENGTH_SHORT,).show()

                        }   else{
                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT,).show()
                        }

                    }
                }
        }

    }
}