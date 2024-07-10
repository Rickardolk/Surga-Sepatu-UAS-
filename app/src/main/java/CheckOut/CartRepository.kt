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


