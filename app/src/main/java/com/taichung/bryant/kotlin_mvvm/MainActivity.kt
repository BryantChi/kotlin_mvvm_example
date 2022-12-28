package com.taichung.bryant.kotlin_mvvm

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.taichung.bryant.kotlin_mvvm.config.Config
import com.taichung.bryant.kotlin_mvvm.databinding.ActivityMainBinding
import com.taichung.bryant.kotlin_mvvm.databinding.DialogListLangBinding
import com.taichung.bryant.kotlin_mvvm.repository.attractions.AttractionsRepository
import com.taichung.bryant.kotlin_mvvm.ui.viewmodel.MainViewModel
import com.taichung.bryant.kotlin_mvvm.utils.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(
) : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        sharedPreferences = applicationContext.getSharedPreferences(Config.DATA, 0)
        sharedPreferences.edit()
            .putString(Config.LANG, "zh-tw")
            .apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }
}
