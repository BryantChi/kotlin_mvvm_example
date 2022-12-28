package com.taichung.bryant.kotlin_mvvm

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.taichung.bryant.kotlin_mvvm.config.Config.DATA
import com.taichung.bryant.kotlin_mvvm.config.Config.LANG
import com.taichung.bryant.kotlin_mvvm.databinding.ActivityMainBinding
import com.taichung.bryant.kotlin_mvvm.utils.PreferenceHelper
import com.taichung.bryant.kotlin_mvvm.utils.PreferenceHelper.get
import com.taichung.bryant.kotlin_mvvm.utils.PreferenceHelper.set
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        sharedPreferences = PreferenceHelper.customPrefs(applicationContext, DATA)
        val checkLang: String = sharedPreferences[LANG]
        if (checkLang.isEmpty()) {
            sharedPreferences.edit {
                sharedPreferences[LANG] = "zh-tw"
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }
}
