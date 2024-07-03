package com.example.surgasepatu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class VerificationActivity : AppCompatActivity() {


    private lateinit var etCode1: EditText
    private lateinit var etCode2: EditText
    private lateinit var etCode3: EditText
    private lateinit var etCode4: EditText
    private lateinit var etCode5: EditText
    private lateinit var etCode6: EditText
    private lateinit var btnVerify: Button
    private lateinit var tvResend: TextView
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.verification)

        etCode1 = findViewById(R.id.etCode1)
        etCode2 = findViewById(R.id.etCode2)
        etCode3 = findViewById(R.id.etCode3)
        etCode4 = findViewById(R.id.etCode4)
        etCode5 = findViewById(R.id.etCode5)
        etCode6 = findViewById(R.id.etCode6)
        btnVerify = findViewById(R.id.buttonverification)
        tvResend = findViewById(R.id.tvresending)
        email = intent.getStringExtra("email")!!

        btnVerify.setOnClickListener {
            val code = "${etCode1.text}${etCode2.text}${etCode3.text}${etCode4.text}${etCode5.text}${etCode6.text}"
            if (code.length == 6) {
                verifyCode(email, code)
            } else {
                Toast.makeText(this, "Please enter the 6-digit code", Toast.LENGTH_SHORT).show()
            }
        }

        tvResend.setOnClickListener {
            // Handle resending code logic
        }
    }

    private fun verifyCode(email: String, code: String) {
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("email", email)
            .add("verification_code", code)
            .build()

        val request = Request.Builder()
            .url("http://localhost/user_registration/verify.php")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@VerificationActivity, "Failed to connect to server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                val jsonResponse = JSONObject(responseData)
                val status = jsonResponse.getString("status")
                val message = jsonResponse.getString("message")

                runOnUiThread {
                    Toast.makeText(this@VerificationActivity, message, Toast.LENGTH_SHORT).show()
                    if (status == "success") {
                        val intent = Intent(this@VerificationActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })

    }
}