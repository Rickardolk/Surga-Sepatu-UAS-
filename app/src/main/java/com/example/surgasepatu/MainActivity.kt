package com.example.surgasepatu

import Menu.CartFragment
import Menu.HistoryFragment
import Menu.HomeFragment
import Menu.ProfileFragment
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.http.Tag

class MainActivity : AppCompatActivity() {

    private var currentFragmentTag: String? = "HOME_FRAGMENT"
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.button_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            var tag: String? = null
            when (item.itemId) {
                R.id.bn_Home -> {
                    selectedFragment = HomeFragment()
                    tag = "HOME_FRAGMENT"
                }
                R.id.bn_Keranjang -> {
                    selectedFragment = CartFragment()
                    tag = "CART_FRAGMENT"
                }
                R.id.bn_History -> {
                    selectedFragment = HistoryFragment()
                    tag = "HISTORY_FRAGMENT"
                }
                R.id.bn_Profile -> {
                    selectedFragment = ProfileFragment()
                    tag = "PROFILE_FRAGMENT"
                }
            }
            if (selectedFragment != null) {
                loadFragment(selectedFragment, tag)
            }
            true
        }

        val navigateTo = intent.getStringExtra("navigateTo")
        if (navigateTo == "cart") {
            bottomNavigationView.selectedItemId = R.id.bn_Keranjang
        } else {
            bottomNavigationView.selectedItemId = R.id.bn_Home
        }
    }


    private fun loadFragment(fragment: Fragment, tag: String?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fm, fragment, tag)
        transaction.addToBackStack(null)
        transaction.commit()
        currentFragmentTag = tag
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)
        if (fragment is CartFragment || fragment is HistoryFragment || fragment is ProfileFragment) {
            val homeFragment = HomeFragment()
            loadFragment(homeFragment, "HOME_FRAGMENT")
            bottomNavigationView.selectedItemId = R.id.bn_Home
        } else {
            super.onBackPressed()
        }
    }
}