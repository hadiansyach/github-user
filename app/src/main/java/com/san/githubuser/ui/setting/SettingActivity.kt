package com.san.githubuser.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.san.githubuser.databinding.ActivitySettingBinding
import com.san.githubuser.ui.viewmodel.SettingViewModel
import com.san.githubuser.ui.viewmodel.ViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val settingViewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingViewModel.getThemeSettings().observe(this) { checkTheme: Boolean ->
            binding.switchBtnTheme.isChecked = checkTheme
            if (checkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.switchBtnTheme.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }
}