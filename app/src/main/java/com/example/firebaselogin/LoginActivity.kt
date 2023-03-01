package com.example.firebaselogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaselogin.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase 초기화
        auth = Firebase.auth

        binding.loginButton.setOnClickListener {
            val email = binding.idET.text.toString()
            val password = binding.passwordET.text.toString()

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    if (auth.currentUser != null) {
                        goToMainActivity()
                    }
                } else {
                    signIn(email, password)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // User가 로그인을 했는지 안했는지 확인해주는 로직으로, Activity와 Fragment가 활성화가 되기 전에 실행되어야 하므로 onStart에서 체크해줌
        val currentUser = auth.currentUser
        if (currentUser != null) {

        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                goToMainActivity()
            } else {
                Toast.makeText(this, "로그인을 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToMainActivity() {
        startActivity(MainActivity.getIntent(this))
    }

}