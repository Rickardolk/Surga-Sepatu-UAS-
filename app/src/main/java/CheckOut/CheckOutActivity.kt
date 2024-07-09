package CheckOut

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surgasepatu.R
import com.google.android.material.textfield.TextInputEditText

class CheckOutActivity : AppCompatActivity() {

    private lateinit var addressEditText: TextInputEditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var paymentMethodRadioGroup: RadioGroup
    private lateinit var placeOrderButton: Button
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
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val cartItems = CartRepository.getCartItems()
        recyclerView.adapter = CartAdapter(cartItems, { /* item click*/ }, { /*hapus item*/ })
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
            }
        }

    }

}