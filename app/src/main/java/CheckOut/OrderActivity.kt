package CheckOut

import Data.CartItem
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surgasepatu.MainActivity
import com.example.surgasepatu.R

class OrderActivity : AppCompatActivity() {

    private lateinit var addressTextView: TextView
    private lateinit var paymentMethodTextView: TextView
    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.order)

        addressTextView = findViewById(R.id.address_text_view)
        paymentMethodTextView = findViewById(R.id.payment_method_text_view)
        itemsRecyclerView = findViewById(R.id.items_recycler_view)

        val address = intent.getStringExtra("address")
        val paymentMethod = intent.getStringExtra("paymentMethod")
        val cartItems = intent.getParcelableArrayListExtra<CartItem>("cartItems")

        addressTextView.text = address
        paymentMethodTextView.text = paymentMethod

        setupRecyclerView(cartItems)

        CartRepository.clearCart()

        Toast.makeText(this, "Pesanan berhasil dibuat", Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(cartItems: List<CartItem>?) {
        itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(cartItems ?: emptyList(), { /* item click*/ }, { /*hapus item*/ })
        itemsRecyclerView.adapter = cartAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("navigateTo", "cart")
        startActivity(intent)
        finish()
    }
}