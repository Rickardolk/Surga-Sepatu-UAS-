package ItemProduct

import CheckOut.CartItem
import CheckOut.CartRepository
import CheckOut.CheckOutActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.surgasepatu.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.detail)

        val imageView: ImageView = findViewById(R.id.product_image)
        val brandTextView: TextView = findViewById(R.id.product_brand)
        val nameTextView: TextView = findViewById(R.id.product_name)
        val sizeTextView: TextView = findViewById(R.id.product_size)
        val statusTextView: TextView = findViewById(R.id.product_status)
        val oldPriceTextView: TextView = findViewById(R.id.product_old_price)
        val newPriceTextView: TextView = findViewById(R.id.product_new_price)
        val ratingTextView: TextView = findViewById(R.id.product_rating)
        val btnAddToCart: Button = findViewById(R.id.btn_add_to_cart)
        val btnCheckout: Button = findViewById(R.id.btn_checkout)

        val image = intent.getStringExtra("image")
        val brand = intent.getStringExtra("brand")
        val name = intent.getStringExtra("name")
        val size = intent.getIntExtra("size", 0)
        val status = intent.getStringExtra("status")
        val oldPrice = intent.getStringExtra("oldPrice")
        val newPrice = intent.getStringExtra("newPrice")
        val rating = intent.getFloatExtra("rating", 0.0f)

        Glide.with(this).load(image).into(imageView)
        brandTextView.text = brand
        nameTextView.text = name
        sizeTextView.text = "Size $size"
        statusTextView.text = status
        oldPriceTextView.text = oldPrice
        newPriceTextView.text = newPrice
        ratingTextView.text = rating.toString()

        btnAddToCart.setOnClickListener {
            val cartItem = CartItem(image, brand, name, "Size $size", status, oldPrice, newPrice, rating.toString())
            CartRepository.addItem(cartItem)
            Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnCheckout.setOnClickListener {
            val cartItem = CartItem(image, brand, name, "Size $size", status, oldPrice, newPrice, rating.toString())
            val intent = Intent(this, CheckOutActivity::class.java)
            intent.putExtra("cartItem", cartItem)
            intent.putExtra("directCheckout", true)
            startActivity(intent)
        }
    }
}