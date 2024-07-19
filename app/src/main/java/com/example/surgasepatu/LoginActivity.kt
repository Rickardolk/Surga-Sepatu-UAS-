package com.example.surgasepatu


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var auth: FirebaseAuth

    private var isPasswordVisible = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)

        etUsername = findViewById(R.id.etusername)
        etPassword = findViewById(R.id.etpassword)
        btnLogin = findViewById(R.id.buttonlogin2)
        auth = FirebaseAuth.getInstance()

        //buat function untuk login
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        val tvSignUpLogin = findViewById<TextView>(R.id.tvsigninlogin)
        tvSignUpLogin.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        //hint password
        val passwordEditText: EditText = findViewById(R.id.etpassword)

        passwordEditText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (passwordEditText.right - passwordEditText.compoundDrawables[2].bounds.width())) {
                    isPasswordVisible = !isPasswordVisible
                    if (isPasswordVisible) {
                        //munculkan
                        passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(
                            ContextCompat.getDrawable(this, R.drawable.ic_lock),
                            null,
                            ContextCompat.getDrawable(this, R.drawable.ic_hint),
                            null
                        )
                    } else {
                        //hilangkan
                        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(
                            ContextCompat.getDrawable(this, R.drawable.ic_lock),
                            null,
                            ContextCompat.getDrawable(this, R.drawable.ic_hint),
                            null
                        )
                    }
                    //kursor
                    passwordEditText.setSelection(passwordEditText.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}