package com.example.finalproject_6_1.Ui.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.finalproject_6_1.R
import com.example.finalproject_6_1.Ui.HomeAdminActivity.HomeAdminActivity
import com.example.finalproject_6_1.Ui.HomeUser.HomeUserActivity
import com.example.finalproject_6_1.databinding.ActivityLoginBinding
import com.example.finalproject_6_1.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private var valid: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this@LoginActivity)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        initListener()
    }
    private fun checkField(textField: EditText): Boolean {
        if (textField.text.toString().isEmpty()) {
            textField.error = "Error"
            valid = false
        } else {
            valid = true
        }

        return valid
    }
    private fun initListener() {
        binding.btnLogin.setOnClickListener {
            onClickSignIn()
        }
        binding.tvForgotPassword.setOnClickListener{
            onClickForgotPassword()
        }
    }
    private fun onClickSignIn() {
        checkField(binding.edtEmailLogin)
        checkField(binding.edtPasswordLog)
        if (valid) {
            auth.signInWithEmailAndPassword(binding.edtEmailLogin.text.toString(), binding.edtPasswordLog.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        task.result.user?.let { checkUserAccessLevel(it.uid) }

                    } else {
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun checkUserAccessLevel(uid: String) {
        val df: DocumentReference = fStore.collection("Users").document(uid)

        df.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.getString("isAdmin") != null) {
                startActivity(Intent(applicationContext, HomeAdminActivity::class.java))
                finish()
            }
            if (documentSnapshot.getString("isUser") != null) {
                startActivity(Intent(applicationContext, HomeUserActivity::class.java))
                finish()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val usersCollection = FirebaseFirestore.getInstance().collection("Users")
            val userDocument = usersCollection.document(userId)

            userDocument.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.getString("isAdmin") != null) {
                    // User is an admin
                    startActivity(Intent(applicationContext, HomeAdminActivity::class.java))
                    finish()
                } else if (documentSnapshot.getString("isUser") != null) {
                    // User is a regular user
                    startActivity(Intent(applicationContext, HomeUserActivity::class.java))
                    finish()
                } else {
                    // User document does not have admin or user access level
                    handleInvalidAccessLevel()
                }
            }.addOnFailureListener {
                // Failed to retrieve user document
                handleInvalidAccessLevel()
            }
        }
    }
    private fun handleInvalidAccessLevel() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }
    private fun onClickForgotPassword() {
        val builder= AlertDialog.Builder(this@LoginActivity)
        val dialogView=layoutInflater.inflate(R.layout.dialog_forgot_password,null)
        val editText=dialogView.findViewById<EditText>(R.id.edt_email)
        builder.setView(dialogView)
        val dialog:AlertDialog=builder.create()
        dialogView.findViewById<Button>(R.id.btn_send_email).setOnClickListener {
            val userEmail=editText.text.toString().trim()
            if (TextUtils.isEmpty(userEmail)&&!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                Toast.makeText(this@LoginActivity, "Nhập địa chỉ Email.", Toast.LENGTH_SHORT).show()
            }
            auth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Đã gửi email đặt lại mật khẩu. Kiểm tra hộp thư điện tử của bạn.", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(this, "Không gửi được email đặt lại mật khẩu. Vui lòng kiểm tra địa chỉ email của bạn.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        dialogView.findViewById<Button>(R.id.btn_close_dialog).setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()

    }
    override fun onBackPressed() {
        // Show an alert dialog to confirm if the user wants to exit
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Thoát ứng dụng")
        alertDialogBuilder.setMessage("Bạn có chắc chắn muốn thoát khỏi ứng dụng không?")
        alertDialogBuilder.setPositiveButton("Có") { _, _ ->
            // If the user confirms, finish the activity to exit the app
            finish()
        }
        alertDialogBuilder.setNegativeButton("Không") { dialog, _ ->
            // If the user cancels, simply dismiss the dialog
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}