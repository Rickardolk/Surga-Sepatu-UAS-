package Menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.surgasepatu.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private val shoeTitles = arrayOf(
        arrayOf("Ponte Nero Terra Dark Choco", "Signore Low Full Black", "Derby Boots Black"), // Formal
        arrayOf("Compass Velocity Black", "Saki Sneakers - Honey Brown", "Tiga Slip-On Sneakers - Black"), // Sneakers
        arrayOf("Leder Loafer 1 Black", "Carter Black Suede Shoes", "Tonsea CH Brown"), // Loafers
        arrayOf("Ignition SV Subs1-Black", "PIERO JOGGER V54 PRM-DARK", "PIERO JOGGER 24 CORE-BLACK"), // Running
        arrayOf("Puntondas Crazy Brown", "Jeune Chukka Oxblood", "SIGNORE BOOTS FULL BLACK")  // boots
    )

    private val shoePrices = arrayOf(
        arrayOf("Rp 458.000,00", "Rp 450.000,00", "Rp 375.000,00"), // Formal
        arrayOf("Rp 798.000,00", "Rp 419.000,00", "Rp 549.000,00"), // Sneakers
        arrayOf("Rp 299.000,00", "Rp 329.000,00", "Rp 335.000,00"), // Loafers
        arrayOf("Rp 699.000,00", "Rp 454.000,00", "Rp 499.000,00"), // running
        arrayOf("Rp 550.000,00", "Rp 550.000,00", "Rp 879.000,00")  // BOots
    )

    private val shoeRatings = arrayOf(
        arrayOf("4.6", "4.4", "4.2"), // Formal
        arrayOf("4.5", "4.7", "4.8"), // Sneakers
        arrayOf("5.0", "4.4", "4.6"), // Loafers
        arrayOf("4.2", "4.1", "4.5"), // Running
        arrayOf("4.6", "4.2", "4.5") // Boots
    )

    private val shoeImages = arrayOf(
        arrayOf(
            "https://bro.do/cdn/shop/files/BRODO-BTW-33_1800x1800.jpg?v=1697441430",
            "https://bro.do/cdn/shop/files/BRODO-BTW-18_1800x1800.jpg?v=1697441371",
            "https://porteegoods.com/cdn/shop/products/original-derby-boots-black-376330_600x.jpg?v=1687856620"
        ), // Formal
        arrayOf(
            "https://i.pinimg.com/564x/4f/a5/eb/4fa5eb845ace53f25aa92b6f6c1f375e.jpg",
            "https://cdn.shopify.com/s/files/1/0404/7552/5283/files/7bbe0ff07667ff1bf8c331f8f96c8cab.jpg?v=1689772628",
            "https://cdn.shopify.com/s/files/1/0404/7552/5283/files/product-description-tiga---feature.jpg?v=1656223969"
        ), // Sneakers
        arrayOf(
            "https://dfw7ggv03f58r.cloudfront.net/accounts/6623815b57eb496f470a6066/products/66333c3dfd989969cf2405ef/66333c3efd989969cf240676.jpeg",
            "https://highty.id/wp-content/uploads/2021/08/Carter01-1.jpg",
            "https://down-id.img.susercontent.com/file/5cfebbbd7e199d1971f242c099adb733"
        ), // Loafers
        arrayOf(
            "https://www.static-src.com/wcsstore/Indraprastha/images/catalog/full/catalog-image/101/MTA-164144089/specs_specs_ignition_sv_subs_1_black_gold_sepatu_running_specs_original_full01_j4zju1f2.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQadqDgMNYr430Zyq5wr7jb15yGocb1Jrelgw&s",
            "https://im.berrybenka.com/assets/upload/product/zoom/59364_piero-jogger-all-black_black_B31IX.jpg"
        ),//Running
        arrayOf(
            "https://media.karousell.com/media/photos/products/2019/07/18/boots_guten_inc_coklat_vintage_puntondas_1563449631_95fe1076.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbTg2XJdeknc7nbXNbN3VCvTDeHcBuNAdGuQ&s",
            "https://down-id.img.susercontent.com/file/cc027b331f14b1314e713dc823f9b2cc"
        )//Boots
    )


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnFormal = view.findViewById<Button>(R.id.btn_formal)
        val btnSneakers = view.findViewById<Button>(R.id.btn_sneakers)
        val btnLoafers = view.findViewById<Button>(R.id.btn_loafers)
        val btnRunning = view.findViewById<Button>(R.id.btn_running)
        val btnBoots = view.findViewById<Button>(R.id.btn_boots)

        val buttons = listOf(btnFormal, btnSneakers, btnLoafers, btnRunning, btnBoots)

        btnFormal.setOnClickListener {
            displayItems(0, view)
            selectButton(btnFormal, buttons)
        }
        btnSneakers.setOnClickListener {
            displayItems(1, view)
            selectButton(btnSneakers, buttons)
        }
        btnLoafers.setOnClickListener {
            displayItems(2, view)
            selectButton(btnLoafers, buttons)
        }
        btnRunning.setOnClickListener {
            displayItems(3, view)
            selectButton(btnRunning, buttons)
        }
        btnBoots.setOnClickListener {
            displayItems(4, view)
            selectButton(btnBoots, buttons)
        }

        displayItems(0, view)
        selectButton(btnFormal, buttons)

        return view
    }

    private fun displayItems(categoryIndex: Int, rootView: View) {
        val shoeTitles = shoeTitles[categoryIndex]
        val shoePrices = shoePrices[categoryIndex]
        val shoeRatings = shoeRatings[categoryIndex]
        val shoeImages = shoeImages[categoryIndex]

        val layoutItems: LinearLayout = rootView.findViewById(R.id.linearLayoutOpsi)
        layoutItems.removeAllViews()

        for (i in shoeTitles.indices) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_shoe, layoutItems, false)

            val textTitle: TextView = view.findViewById(R.id.tvTitle)
            val textPrice: TextView = view.findViewById(R.id.tvPrice)
            val textRating: TextView = view.findViewById(R.id.tvRating)
            val imageShoe: ImageView = view.findViewById(R.id.imageView)

            textTitle.text = shoeTitles[i]
            textPrice.text = shoePrices[i]
            textRating.text = shoeRatings[i]

            Glide.with(this)
                .load(shoeImages[i])
                .into(imageShoe)

            layoutItems.addView(view)
        }
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

    companion object {

        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}