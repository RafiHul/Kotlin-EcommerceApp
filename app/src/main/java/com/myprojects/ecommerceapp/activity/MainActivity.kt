package com.myprojects.ecommerceapp.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.clearLoginInfo
import com.myprojects.ecommerceapp.database.AppDatabase
import com.myprojects.ecommerceapp.databinding.ActivityMainBinding
import com.myprojects.ecommerceapp.repository.AppRepository
import com.myprojects.ecommerceapp.viewmodel.AppViewModel
import com.myprojects.ecommerceapp.viewmodel.AppViewModelFactory
import com.myprojects.ecommerceapp.viewmodel.ProfileViewModel
import com.myprojects.ecommerceapp.viewmodel.ProfileViewModelFactory
import com.myprojects.ecommerceapp.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var appViewModel: AppViewModel
    lateinit var profileViewModel: ProfileViewModel
    lateinit var sharedViewModel: SharedViewModel

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

        //clear user login
        lifecycleScope.launch {
            clearLoginInfo(this@MainActivity)
        }

        //mendeteksi bottomnav
        sharedViewModel.isBottomNavVisible.observe(this){
            binding.navView.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
    private fun SetUpViewModel() {
        val repository = AppRepository(AppDatabase(this))

        val viewModelProviderFactory = AppViewModelFactory(application,repository)
        val profileViewModelFactory = ProfileViewModelFactory(repository)

        appViewModel = ViewModelProvider(this,viewModelProviderFactory).get(AppViewModel::class.java)
        profileViewModel = ViewModelProvider(this,profileViewModelFactory).get(ProfileViewModel::class.java)
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
    }
}
