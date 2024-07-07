package ItemProduct

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surgasepatu.R

class ListProduct : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.list_product)


        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val productList = generateDummyProductList()
        val adapter = ProductAdapter(productList)
        recyclerView.adapter = adapter

    }

    private fun generateDummyProductList(): List<Product_kt> {
        return listOf(
            Product_kt("https://bro.do/cdn/shop/files/2_d684af50-6e4c-4ff6-802e-6bf506ee798b_1800x1800.jpg?v=1711098911", "Brodo", "Ponte Nero Terra Dark Choco", "Size 42", "Pre - Order", "Rp 500.000,00", "Rp 450.000,00", "4.6"),
            Product_kt("https://compass-ecom-bucket.s3-ap-southeast-1.amazonaws.com/images/productdetail/f95170a5f613fa35ca5f3e2323a932cff230713b.png", "Compass", "Compass Velocity Black", "Size 40", "Open - Order", "Rp 798.000,00", "rp 718.000,00", "4.3"),
            Product_kt("https://www.specs.id/media/catalog/product/s/p/spe110400016-1.jpg?optimize=high&bg-color=255,255,255&fit=bounds&height=1683&width=1683&canvas=1683:1683", "Specs Id", "Ignition SV Subs1-Black", "Size 43", "Open - Order", "Rp 699.000,00", "Rp 629.000,00", "4.6"),
            Product_kt("https://guteninc-id.myshopify.com/cdn/shop/products/jeune-chukka-oxblood-3_700x.jpg?v=1564472267", "Guten Inc", "Jeune Chukka Oxblood", "Size 40", "Pre - Order", "Rp 500.000,00", "Rp 450.000,00", "4.6"),
            Product_kt("https://bro.do/cdn/shop/products/481-2_800x800_e7ee0392-1853-43b9-9f2a-023d3a1c9e33_1800x1800.jpg?v=1579601057", "Brodo", "Signore Low Full Black", "Size 41", "Open - Order", "Rp 500.000,00", "Rp 450.000,00", "4.2"),
            Product_kt("https://www.ginomariani.com/cdn/shop/products/56290b98-e1a8-4c5c-90d8-d9456bba9409-6.jpg?v=1707209151&width=600", "Ginomariani", "Alesto Dark Brown", "Size 41", "Pre - Order", "Rp 550.000,00", "Rp 495.000,00", "5.0"),

        )
    }


}