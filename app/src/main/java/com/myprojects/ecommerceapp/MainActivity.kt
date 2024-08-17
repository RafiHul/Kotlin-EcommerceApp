package com.myprojects.ecommerceapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.myprojects.ecommerceapp.database.AppDatabase
import com.myprojects.ecommerceapp.databinding.ActivityMainBinding
import com.myprojects.ecommerceapp.repository.AppRepository
import com.myprojects.ecommerceapp.viewmodel.AppViewModel
import com.myprojects.ecommerceapp.viewmodel.AppViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var appViewModel: AppViewModel

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

        lifecycleScope.launch {
            clearLoginInfo(this@MainActivity)
        }

    }

    private fun SetUpViewModel() {
        val repository = AppRepository(AppDatabase(this))

        val viewModelProviderFactory = AppViewModelFactory(application,repository)

        appViewModel = ViewModelProvider(this,viewModelProviderFactory).get(AppViewModel::class.java)
    }
}