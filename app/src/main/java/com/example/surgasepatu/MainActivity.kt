package com.example.surgasepatu

import Menu.CartFragment
import Menu.HistoryFragment
import Menu.HomeFragment
import Menu.ProfileFragment
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.button_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.bn_Home -> selectedFragment = HomeFragment()
                R.id.bn_Keranjang -> selectedFragment = CartFragment()
                R.id.bn_History -> selectedFragment = HistoryFragment()
                R.id.bn_Profile -> selectedFragment = ProfileFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fm, selectedFragment).commit()
            }
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.bn_Home
    }
}