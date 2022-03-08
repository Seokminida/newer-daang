package com.proj.newer_daang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_2.*
import kotlinx.android.synthetic.main.activity_login_2.email_l
import kotlinx.android.synthetic.main.activity_login_2.login_b
import kotlinx.android.synthetic.main.activity_login_2.password_l
import kotlinx.android.synthetic.main.activity_login_2.*
import kotlinx.android.synthetic.main.activity_signup.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_2)
        auth = Firebase.auth
        //파이어베이스 로그인
        login_b.setOnClickListener {
            var email = email_l.text.toString()
            var password = password_l.text.toString()
            if (email.length == 0 || password.length == 0) {
                Toast.makeText(
                    baseContext, "빈 칸이 있습니다.",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext, "로그인에 실패하였습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                        }
                    }
            }
        }

        signup.setOnClickListener {
            val intent = Intent(this, SignActivity::class.java)
            startActivity(intent)
        }

    }
    //자동로그인
    public override fun onStart() {
        super.onStart()
        updateUI(auth?.currentUser)
    }


    private fun updateUI(user: FirebaseUser?) { //update ui code here
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        }
    }

}