package Menu

import Data.Order
import Activity.ListProductActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
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
import java.text.SimpleDateFormat
import java.util.Date


class HistoryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orderList: MutableList<Order>
    private lateinit var deleteButton: Button

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

        deleteButton = view.findViewById(R.id.delete_button)
        deleteButton.setOnClickListener{
            deleteSelectedOrders()
        }

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
                    val order = orderSnapshot.getValue(Order::class.java)?.copy(orderId = orderSnapshot.key ?: "")
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

    private fun deleteSelectedOrders() {
        val selectedOrders = orderAdapter.getSelectedOrders()
        val database = FirebaseDatabase.getInstance()
        val ordersRef = database.getReference("orders")

        selectedOrders.forEach { order ->
            ordersRef.child(order.orderId).removeValue()
        }

        orderAdapter.removeSelectedOrders()
    }

    inner class OrderAdapter(private val orderList: List<Order>) :
        RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

        private val selectedOrders = mutableSetOf<Order>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order, parent, false)
            return OrderViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
            val currentOrder = orderList[position]
            holder.bind(currentOrder, selectedOrders.contains(currentOrder))

        }

        override fun getItemCount() = orderList.size

        fun getSelectedOrders(): Set<Order> {
            return selectedOrders
        }

        fun removeSelectedOrders() {
            val ordersToRemove = selectedOrders.toList()
            ordersToRemove.forEach { order ->
                val position = orderList.indexOf(order)
                if (position != -1) {
                    (orderList as MutableList).removeAt(position)
                    notifyItemRemoved(position)
                }
            }
            selectedOrders.clear()
        }

        inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val addressTextView: TextView = itemView.findViewById(R.id.order_address)
            private val paymentMethodTextView: TextView = itemView.findViewById(R.id.order_payment_method)
            private val orderTimeTextView: TextView = itemView.findViewById(R.id.order_time)
            private val itemsTextView: TextView = itemView.findViewById(R.id.order_items)
            private val checkBox: CheckBox = itemView.findViewById(R.id.order_checkbox)

            fun bind(order: Order, isSelected: Boolean) {
                addressTextView.text = order.address
                paymentMethodTextView.text = order.paymentMethod
                orderTimeTextView.text = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(order.orderTime))
                itemsTextView.text = order.items.joinToString { it.name.toString() }
                checkBox.isChecked = isSelected

                itemView.setOnClickListener {
                    checkBox.isChecked = !checkBox.isChecked
                    if (checkBox.isChecked) {
                        selectedOrders.add(order)
                    } else {
                        selectedOrders.remove(order)
                    }
                }

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedOrders.add(order)
                    } else {
                        selectedOrders.remove(order)
                    }
                }
            }
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