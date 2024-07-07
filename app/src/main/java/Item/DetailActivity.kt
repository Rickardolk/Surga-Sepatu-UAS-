package Item

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        val image = intent.getStringExtra("image")
        val brand = intent.getStringExtra("brand")
        val name = intent.getStringExtra("name")
        val size = intent.getStringExtra("size")
        val status = intent.getStringExtra("status")
        val oldPrice = intent.getStringExtra("oldPrice")
        val newPrice = intent.getStringExtra("newPrice")
        val rating = intent.getStringExtra("rating")

        Glide.with(this).load(image).into(imageView)
        brandTextView.text = brand
        nameTextView.text = name
        sizeTextView.text = size
        statusTextView.text = status
        oldPriceTextView.text = oldPrice
        newPriceTextView.text = newPrice
        ratingTextView.text = rating
    }
}