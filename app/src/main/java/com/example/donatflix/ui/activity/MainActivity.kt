package com.example.donatflix.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.donatflix.databinding.ActivityMainBinding
import com.example.api.IRegistrationLauncher
import com.example.api.IFragmentReplace
import com.example.api.IUserInfo
import com.example.donatflix.R
import com.example.donatflix.databinding.MenuHeaderBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(), IFragmentReplace, IUserInfo, KoinComponent {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTheme(R.style.Theme_Donatflix)

        val launcher: IRegistrationLauncher by inject()

        setFragment(launcher.launch() as Fragment)

        controlNavigationMenu()
    }

    private fun controlNavigationMenu() {
        binding.nvMenu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> Toast.makeText(
                    this@MainActivity,
                    R.string.nv_settings,
                    Toast.LENGTH_LONG
                ).show()

                R.id.about -> Toast.makeText(
                    this@MainActivity,
                    R.string.nv_about,
                    Toast.LENGTH_LONG
                ).show()

                R.id.exit -> {}
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onUserDataReceived(login: String, image: String, favoriteFilms: List<Int>) {
        val header = binding.nvMenu.getHeaderView(0)
        val headerBinding = MenuHeaderBinding.bind(header)

        headerBinding.tvLogin.text = login
        CoroutineScope(Dispatchers.Main).launch {
            Glide.with(this@MainActivity)
                .load(image)
                .into(headerBinding.imageUserIcon)
        }
    }
}