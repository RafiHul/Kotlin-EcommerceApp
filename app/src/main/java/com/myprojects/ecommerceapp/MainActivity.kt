package com.myprojects.ecommerceapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myprojects.ecommerceapp.database.ItemDatabase
import com.myprojects.ecommerceapp.database.UserDatabase
import com.myprojects.ecommerceapp.databinding.ActivityMainBinding
import com.myprojects.ecommerceapp.repository.ItemRepository
import com.myprojects.ecommerceapp.repository.UserRepository
import com.myprojects.ecommerceapp.viewmodel.ItemViewModel
import com.myprojects.ecommerceapp.viewmodel.ItemViewModelFactory
import com.myprojects.ecommerceapp.viewmodel.UserViewModel
import com.myprojects.ecommerceapp.viewmodel.UserViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var itemViewModel: ItemViewModel
    lateinit var userViewModel: UserViewModel

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
        SetUpUserViewModel()

        lifecycleScope.launch {
            clearLoginInfo(this@MainActivity)
        }

    }

    private fun SetUpUserViewModel() {
        val repository = UserRepository(UserDatabase(this))

        val viewModelProviderFactory = UserViewModelFactory(application,repository)

        userViewModel = ViewModelProvider(this,viewModelProviderFactory).get(UserViewModel::class.java)
    }

    private fun SetUpViewModel() {
        val repository = ItemRepository(ItemDatabase(this))

        val viewModelProviderFactory = ItemViewModelFactory(application,repository)

        itemViewModel = ViewModelProvider(this,viewModelProviderFactory).get(ItemViewModel::class.java)
    }
}