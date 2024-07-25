package Data

import Activity.DetailActivity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.surgasepatu.R

//product adapter
class ProductAdapter (private var productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        Glide.with(holder.itemView.context)
            .load(currentProduct.image)
            .into(holder.imageView)
        holder.brandTextView.text = currentProduct.brand
        holder.nameTextView.text = currentProduct.name
        holder.sizeTextView.text = "Size ${currentProduct.size}"
        holder.statusTextView.text = currentProduct.status
        holder.oldPriceTextView.text = currentProduct.oldPrice
        holder.newPriceTextView.text = currentProduct.newPrice
        holder.ratingTextView.text = currentProduct.rating.toString()

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("image", currentProduct.image)
                putExtra("brand", currentProduct.brand)
                putExtra("name", currentProduct.name)
                putExtra("size", currentProduct.size)
                putExtra("status", currentProduct.status)
                putExtra("oldPrice", currentProduct.oldPrice)
                putExtra("newPrice", currentProduct.newPrice)
                putExtra("rating", currentProduct.rating)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = productList.size

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.product_image)
        val brandTextView: TextView = itemView.findViewById(R.id.product_brand)
        val nameTextView: TextView = itemView.findViewById(R.id.product_name)
        val sizeTextView: TextView = itemView.findViewById(R.id.product_size)
        val statusTextView: TextView = itemView.findViewById(R.id.product_status)
        val oldPriceTextView: TextView = itemView.findViewById(R.id.product_old_price)
        val newPriceTextView: TextView = itemView.findViewById(R.id.product_new_price)
        val ratingTextView: TextView = itemView.findViewById(R.id.product_rating)
    }
}
//cart adapter
class CartAdapter(
    var cartItems: List<CartItem>,
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


