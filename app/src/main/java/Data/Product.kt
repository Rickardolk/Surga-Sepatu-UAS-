package Data

import CheckOut.CartItem

data class Product(
    val name: String = "",
    val brand: String = "",
    val size: Int = 0,
    val status: String = "",
    val oldPrice: String = "",
    val newPrice: String = "",
    val rating: Float = 0.0f,
    val image: String = ""
)
data class Order(
    val orderId: String = "",
    val address: String = "",
    val paymentMethod: String = "",
    val items: List<CartItem> = emptyList(),
    val orderTime: Long = 0L
)
