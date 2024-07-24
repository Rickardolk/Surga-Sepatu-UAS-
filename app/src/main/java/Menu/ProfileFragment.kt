package Menu

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.surgasepatu.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val logOut = view.findViewById<ConstraintLayout>(R.id.cl_logout)
        logOut.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure to exit?")
                .setPositiveButton("Yes") { dialog, id ->
                    activity?.finishAffinity()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            builder.create().show()
        }
        return view
    }
}