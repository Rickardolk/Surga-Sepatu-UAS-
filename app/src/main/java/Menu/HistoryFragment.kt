package Menu

import ItemProduct.ListProduct
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.surgasepatu.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HistoryFragment : Fragment() {

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

        val view = inflater.inflate(R.layout.fragment_history, container,false)

        val llMarket = view.findViewById<LinearLayout>(R.id.llmarket)
        llMarket.setOnClickListener{
            val intent = Intent(activity, ListProduct::class.java)
            startActivity(intent)
        }

        val llBuynow = view.findViewById<LinearLayout>(R.id.llbuynow)
        llBuynow.setOnClickListener{
            navigateToCartFragment()
        }
        return view
    }

    private fun navigateToCartFragment() {
        val cartFragment = CartFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.fm_history, cartFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}