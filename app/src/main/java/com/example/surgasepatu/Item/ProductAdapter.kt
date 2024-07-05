package com.example.surgasepatu.Item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.surgasepatu.R
import retrofit2.http.Url

class ProductAdapter (private val productList: List<Product_kt>) :
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
        holder.sizeTextView.text = currentProduct.size
        holder.statusTextView.text = currentProduct.status
        holder.oldPriceTextView.text = currentProduct.oldPrice
        holder.newPriceTextView.text = currentProduct.newPrice
        holder.ratingTextView.text = currentProduct.rating
    }

    override fun getItemCount() = productList.size

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
