package CheckOut

object CartRepository {
    private val cartItems = mutableListOf<CartItem>()

    fun addItem(item: CartItem) {
        cartItems.add(item)
    }

    fun removeItem(item: CartItem) {
        cartItems.remove(item)
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getCartItems(): List<CartItem> = cartItems
}

data class CartItem(
    val image: String?,
    val brand: String?,
    val name: String?,
    val size: String?,
    val status: String?,
    val oldPrice: String?,
    val newPrice: String?,
    val rating: String?
)
