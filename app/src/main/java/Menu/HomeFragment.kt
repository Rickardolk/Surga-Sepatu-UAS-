package Menu

import Activity.DetailActivity
import Activity.ListProductActivity
import Data.Product
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.surgasepatu.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var linearLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = FirebaseDatabase.getInstance().reference.child("product")

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnFormal = view.findViewById<Button>(R.id.btn_formal)
        val btnSneakers = view.findViewById<Button>(R.id.btn_sneakers)
        val btnLoafers = view.findViewById<Button>(R.id.btn_loafers)
        val btnRunning = view.findViewById<Button>(R.id.btn_running)
        val btnBoots = view.findViewById<Button>(R.id.btn_boots)
        val buttons = listOf(btnFormal, btnSneakers, btnLoafers, btnRunning, btnBoots)

        linearLayout = view.findViewById(R.id.llsearch)

        btnFormal.setOnClickListener {
            displayItems("formal", view)
            selectButton(btnFormal, buttons)
        }
        btnSneakers.setOnClickListener {
            displayItems("sneakers", view)
            selectButton(btnSneakers, buttons)
        }
        btnLoafers.setOnClickListener {
            displayItems("loafers", view)
            selectButton(btnLoafers, buttons)
        }
        btnRunning.setOnClickListener {
            displayItems("running", view)
            selectButton(btnRunning, buttons)
        }
        btnBoots.setOnClickListener {
            displayItems("boots", view)
            selectButton(btnBoots, buttons)
        }

        displayItems("formal", view)
        selectButton(btnFormal, buttons)

        linearLayout.setOnClickListener {
            val intent = Intent(context, ListProductActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    //item display
    private fun displayItems(category: String, rootView: View) {
        val layoutItems: LinearLayout = rootView.findViewById(R.id.linearLayoutOpsi)
        layoutItems.removeAllViews()

        database.orderByKey().startAt(category).endAt(category + "\uf8ff")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        try {
                            val product = snapshot.getValue(Product::class.java)
                            Log.d("FirebaseData", "Product: ${product}")
                            product?.let { addItemView(it, layoutItems) }
                        } catch (e: DatabaseException) { //pesan eror
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("FirebaseData", "Error: ${databaseError.message}")
                }
            })
    }

    @SuppressLint("CheckResult")
    private fun addItemView(product: Product, layoutItems: LinearLayout) {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shoe, layoutItems, false)

        val textTitle: TextView = view.findViewById(R.id.tvTitle)
        val textPrice: TextView = view.findViewById(R.id.tvPrice)
        val textRating: TextView = view.findViewById(R.id.tvRating)
        val imageShoe: ImageView = view.findViewById(R.id.imageView)

        textTitle.text = product.name
        textPrice.text = product.newPrice
        textRating.text = product.rating.toString()

        Glide.with(this)
            .load(product.image)
            .placeholder(R.color.birumuda)
            .error(R.color.birumuda)
            .into(imageShoe)

        view.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("image", product.image)
                putExtra("brand", product.brand)
                putExtra("name", product.name)
                putExtra("size", product.size)
                putExtra("status", product.status)
                putExtra("oldPrice", product.oldPrice)
                putExtra("newPrice", product.newPrice)
                putExtra("rating", product.rating)
            }
            startActivity(intent)
        }

        layoutItems.addView(view)
    }

    private fun selectButton(selectedButton: Button, buttons: List<Button>) {
        for (button in buttons) {
            if (button == selectedButton) {
                button.setBackgroundResource(R.drawable.button_background_selected)
                button.setTextColor(resources.getColor(android.R.color.white))
                button.elevation = 4f
                button.translationZ = 4f
            } else {
                button.setBackgroundResource(R.drawable.button_background)
                button.setTextColor(resources.getColor(android.R.color.darker_gray))
                button.elevation = 0f
                button.translationZ = 0f
            }
        }
    }
}