package Menu

import CheckOut.CartAdapter
import CheckOut.CartItem
import CheckOut.CartRepository
import ItemProduct.DetailActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ItemProduct.ListProduct
import android.app.AlertDialog
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surgasepatu.R

class CartFragment : Fragment() {

    private lateinit var emptyCartView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        val btnSeeProduct: Button = view.findViewById(R.id.buttonseeproduct)
        val deleteImageView: ImageView = view.findViewById(R.id.hapus)
        emptyCartView = view.findViewById(R.id.empty_cart_container)
        recyclerView = view.findViewById(R.id.cart_recycler_view)

        btnSeeProduct.setOnClickListener {
            val intent = Intent(activity, ListProduct::class.java)
            startActivity(intent)
        }

        deleteImageView.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        setupRecyclerView()

        return view
    }

    override fun onResume() {
        super.onResume()
        updateCartView()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        cartAdapter = CartAdapter(CartRepository.getCartItems(), ::onCartItemClick, ::onCartItemDelete)
        recyclerView.adapter = cartAdapter
    }

    private fun updateCartView() {
        val cartItems = CartRepository.getCartItems()
        if (cartItems.isEmpty()) {
            emptyCartView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyCartView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            cartAdapter.updateCartItems(cartItems)
        }
    }

    private fun onCartItemClick(cartItem: CartItem) {
        val intent = Intent(activity, DetailActivity::class.java).apply {
            putExtra("image", cartItem.image)
            putExtra("brand", cartItem.brand)
            putExtra("name", cartItem.name)
            putExtra("size", cartItem.size)
            putExtra("status", cartItem.status)
            putExtra("oldPrice", cartItem.oldPrice)
            putExtra("newPrice", cartItem.newPrice)
            putExtra("rating", cartItem.rating)
        }
        startActivity(intent)
    }

    private fun onCartItemDelete(cartItem: CartItem) {
        CartRepository.removeItem(cartItem)
        updateCartView()
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(context)
            .setTitle("Konfirmasi Hapus")
            .setMessage("Apakah Anda yakin akan menghapus semua item?")
            .setPositiveButton("Ya") { _, _ ->
                CartRepository.clearCart()
                updateCartView()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }


}