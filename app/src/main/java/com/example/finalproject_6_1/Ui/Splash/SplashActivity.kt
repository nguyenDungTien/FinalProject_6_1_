package com.example.finalproject_6_1.Ui.Splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.view.WindowCompat
import com.example.finalproject_6_1.MainActivity
import com.example.finalproject_6_1.R
import com.example.finalproject_6_1.Ui.HomeAdminActivity.HomeAdminActivity
import com.example.finalproject_6_1.Ui.HomeUser.HomeUserActivity
import com.example.finalproject_6_1.Ui.Login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed({
            nextActivity()
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
        }, 3000) // 3000ms = 3 giây
    }
    private fun nextActivity() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val usersCollection = FirebaseFirestore.getInstance().collection("Users")
            val userDocument = usersCollection.document(userId)

            userDocument.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documentSnapshot = task.result
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        if (documentSnapshot.getString("isAdmin") != null) {
                            // User is an admin
                            startActivity(Intent(this, HomeAdminActivity::class.java))
                        } else if (documentSnapshot.getString("isUser") != null) {
                            // User is a regular user
                            startActivity(Intent(this, HomeUserActivity::class.java))
                        } else {
                            // User document does not have admin or user access level
                            handleInvalidAccessLevel()
                        }
                    } else {
                        // Document does not exist or is null
                        handleInvalidAccessLevel()
                    }
                } else {
                    // Failed to retrieve user document
                    handleInvalidAccessLevel()
                }
                finish()
            }
        } else {
            // Current user is null (not logged in)
            handleInvalidAccessLevel()
        }
    }

    private fun handleInvalidAccessLevel() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}