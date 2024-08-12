package com.myprojects.ecommerceapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myprojects.ecommerceapp.database.ItemDatabase
import com.myprojects.ecommerceapp.databinding.ActivityMainBinding
import com.myprojects.ecommerceapp.repository.ItemRepository
import com.myprojects.ecommerceapp.viewmodel.ItemViewModel
import com.myprojects.ecommerceapp.viewmodel.ItemViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var itemViewModel: ItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bottom navbar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)

        SetUpViewModel()
    }

    private fun SetUpViewModel() {
        val repository = ItemRepository(ItemDatabase(this))

        val viewModelProviderFactory = ItemViewModelFactory(application,repository)

        itemViewModel = ViewModelProvider(this,viewModelProviderFactory).get(ItemViewModel::class.java)
    }
}