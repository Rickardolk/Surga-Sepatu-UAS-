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


class RegisterActivity : AppCompatActivity() {

    private var isPasswordVisible = false
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var auth: FirebaseAuth

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register)

        etUsername = findViewById(R.id.etusernameregister)
        etEmail = findViewById(R.id.etemailgerister)
        etPassword = findViewById(R.id.etpasswordregister)
        btnRegister = findViewById(R.id.buttonregister2)
        auth = FirebaseAuth.getInstance()

        val tvloginRegister = findViewById<TextView>(R.id.tvloginregister)
        tvloginRegister.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        //hint password
        val passwordEditText: EditText = findViewById(R.id.etpasswordregister)

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

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}