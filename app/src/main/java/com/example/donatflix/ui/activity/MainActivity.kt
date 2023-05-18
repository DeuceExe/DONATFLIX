package com.example.donatflix.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.donatflix.databinding.ActivityMainBinding
import com.example.api.IFilmFragmentLauncher
import com.example.api.IFilmFragmentReplace
import com.example.donatflix.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(), IFilmFragmentReplace, KoinComponent {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTheme(R.style.Theme_Donatflix)

        val launcher: IFilmFragmentLauncher by inject()

        setFragment(launcher.launch() as Fragment)
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, "$fragment")
            .commit()
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}