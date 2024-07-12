package CheckOut

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surgasepatu.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class CheckOutActivity : AppCompatActivity() {

    private lateinit var addressEditText: TextInputEditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var paymentMethodRadioGroup: RadioGroup
    private lateinit var placeOrderButton: Button
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.check_out)

        addressEditText = findViewById(R.id.address_edit_text)
        recyclerView = findViewById(R.id.item_list_recycler_view)
        paymentMethodRadioGroup = findViewById(R.id.payment_method_radio_group)
        placeOrderButton = findViewById(R.id.place_order_button)

        setupRecyclerView()
        setupPlaceOrderButton()

        val cartItem: CartItem? = intent.getParcelableExtra("cartItem")
        cartItem?.let {
            CartRepository.addItem(it)
            cartAdapter.updateCartItems(CartRepository.getCartItems())
        }

        checkNotificationPermission()

    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val cartItems = CartRepository.getCartItems()
        cartAdapter = CartAdapter(cartItems, { /* item click*/ }, { /*hapus item*/ })
        recyclerView.adapter = cartAdapter

    }

    private fun setupPlaceOrderButton() {
        placeOrderButton.setOnClickListener {
            val address = addressEditText.text.toString()
            val selectedPaymentMethodId = paymentMethodRadioGroup.checkedRadioButtonId

            val paymentMethod = when (selectedPaymentMethodId) {
                R.id.payment_cod -> "COD"
                R.id.payment_mobile_banking -> "Mobile Banking"
                R.id.payment_dana -> "Dana"
                else -> ""
            }

            if (address.isNotEmpty() && paymentMethod.isNotEmpty()) {
                // database mysql
                showConfirmationDialog()
            }
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Pesanan")
        builder.setMessage("Apakah Anda akan memesan produk ini?")
        builder.setPositiveButton("Ya") { dialog, _ ->
            showOrderNotification()
            navigateToOrderActivity()
            showSnackbar("Pesanan berhasil dibuat")
        }
        builder.setNegativeButton("Tidak", null)
        builder.show()

    }

    private fun navigateToOrderActivity() {
        val intent = Intent(this, OrderActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun showOrderNotification() {
        val channelId = "order_channel"
        val notificationId = 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Order Channel"
            val descriptionText = "Notification for order confirmation"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_order)
            .setContentTitle("Surga Sepatu")
            .setContentText("Pesanan berhasil dibuat")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, builder.build())
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                showOrderNotification()
            } else {
            }
        }
    }
    private fun showSnackbar(message: String) {
        val rootView = window.decorView.rootView
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }
}