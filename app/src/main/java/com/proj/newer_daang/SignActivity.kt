package com.proj.newer_daang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_signup.*
import com.google.firebase.auth.FirebaseUser




class SignActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = Firebase.auth
        //파이어베이스 회원가입
        continueB.setOnClickListener {
            if(nickname.text.length == 0 ||password_edittext.text.length == 0||password2_edittext.text.length == 0||email_edittext.text.length == 0){
                Toast.makeText(
                    baseContext, "비어있는 칸이 있습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                if(password_edittext.text.toString() == password2_edittext.text.toString()) { // 패스워드가 동일하다면
                    var email = email_edittext.text.toString()
                    var password = password_edittext.text.toString()
                    //회원가입
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("success", "createUserWithEmail:success")
                                val user = auth.currentUser
                                updateUI(user)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("faile", "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                    baseContext, "회원가입에 실패하였습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                updateUI(null)
                            }
                        }
                }
                else{
                    Toast.makeText(
                        baseContext, "비밀번호가 동일하지 않습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        loginB.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }
    private fun updateUI(user: FirebaseUser?) { //update ui code here
        if (user != null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    
}