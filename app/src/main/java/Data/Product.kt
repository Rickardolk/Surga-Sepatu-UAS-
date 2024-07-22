package Data

import android.os.Parcel
import android.os.Parcelable

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
data class CartItem(
    val image: String?,
    val brand: String?,
    val name: String?,
    val size: String?,
    val status: String?,
    val oldPrice: String?,
    val newPrice: String?,
    val rating: String?
): Parcelable {

    constructor() : this(null, null, null, null, null, null, null, null)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(brand)
        parcel.writeString(name)
        parcel.writeString(size)
        parcel.writeString(status)
        parcel.writeString(oldPrice)
        parcel.writeString(newPrice)
        parcel.writeString(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(parcel: Parcel): CartItem {
            return CartItem(parcel)
        }

        override fun newArray(size: Int): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }
}
