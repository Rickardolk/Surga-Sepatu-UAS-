package CheckOut

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.surgasepatu.MainActivity
import com.example.surgasepatu.R

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.order)

        CartRepository.clearCart()

        Toast.makeText(this, "Pesanan berhasil dibuat", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("navigateTo", "cart")
        startActivity(intent)
        finish()
    }
}