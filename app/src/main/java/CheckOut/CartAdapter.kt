package CheckOut

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.surgasepatu.R

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onItemClick: (CartItem) -> Unit,
    private val onItemDelete: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.cart_item_image)
        private val nameTextView: TextView = itemView.findViewById(R.id.cart_item_name)
        private val priceTextView: TextView = itemView.findViewById(R.id.cart_item_price)
        private val deleteImageView: ImageView = itemView.findViewById(R.id.cart_item_delete)

        fun bind(cartItem: CartItem) {
            Glide.with(itemView.context).load(cartItem.image).into(imageView)
            nameTextView.text = cartItem.name
            priceTextView.text = cartItem.newPrice
            itemView.setOnClickListener { onItemClick(cartItem) }
            deleteImageView.setOnClickListener { onItemDelete(cartItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateCartItems(newCartItems: List<CartItem>) {
        cartItems = newCartItems
        notifyDataSetChanged()
    }
}