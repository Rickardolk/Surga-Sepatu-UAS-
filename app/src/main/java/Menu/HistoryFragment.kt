package Menu

import Data.Order
import ItemProduct.ListProductActivity
import ItemProduct.ProductAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surgasepatu.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import okhttp3.internal.notify
import java.text.SimpleDateFormat
import java.util.Date


class HistoryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orderList: MutableList<Order>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_history, container, false)
        recyclerView = view.findViewById(R.id.order_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        orderList = mutableListOf()
        orderAdapter = OrderAdapter(orderList)
        recyclerView.adapter = orderAdapter

        val llMarket = view.findViewById<LinearLayout>(R.id.llmarket)
        llMarket.setOnClickListener {
            val intent = Intent(activity, ListProductActivity::class.java)
            startActivity(intent)
        }

        val llBuynow = view.findViewById<LinearLayout>(R.id.llbuynow)
        llBuynow.setOnClickListener {
            navigateToCartFragment()
        }

        fetchOrders()

        return view
    }

    private fun fetchOrders() {
        val database = FirebaseDatabase.getInstance()
        val ordersRef = database.getReference("orders")

        ordersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList.clear()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(Order::class.java)
                    if (order != null) {
                        orderList.add(order)
                    } else {
                        Log.e("HistoryFragment", "Order data is null")
                    }
                }
                orderAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HistoryFragment", "Database error: ${error.message}")
            }
        })
    }

    class OrderAdapter(private val orderList: List<Order>) :
        RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order, parent, false)
            return OrderViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
            val currentOrder = orderList[position]
            holder.addressTextView.text = currentOrder.address
            holder.paymentMethodTextView.text = currentOrder.paymentMethod
            holder.orderTimeTextView.text =
                SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(currentOrder.orderTime))
            holder.itemsTextView.text = currentOrder.items.joinToString { it.name.toString() }
        }

        override fun getItemCount() = orderList.size

        class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val addressTextView: TextView = itemView.findViewById(R.id.order_address)
            val paymentMethodTextView: TextView = itemView.findViewById(R.id.order_payment_method)
            val orderTimeTextView: TextView = itemView.findViewById(R.id.order_time)
            val itemsTextView: TextView = itemView.findViewById(R.id.order_items)
        }
    }
    private fun navigateToCartFragment() {
        val cartFragment = CartFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.fm_history, cartFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}