package com.example.impl.presentation.registration

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.api.IFilmFragment
import com.example.api.IFragmentReplace
import com.example.impl.databinding.FragmentRegistrationBinding
import com.example.impl.presentation.fragments.film.FilmsFragment
import com.example.impl.presentation.fragments.film.filmDetails.FilmDetailFragment
import com.example.impl.room.UserDataBase
import com.example.impl.room.entity.User
import org.koin.core.component.KoinComponent
import java.util.regex.Pattern

fun String.validatePassword(): Boolean = Pattern.compile(PASSWORD_PATTERN).matcher(this).matches()
const val PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"

class RegistrationFragment : Fragment(), IFilmFragment, KoinComponent {

    private var _binding: FragmentRegistrationBinding? = null
    val binding get() = requireNotNull(_binding)

    private var fragmentChangeListener: IFragmentReplace? = null

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    private lateinit var db: UserDataBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = UserDataBase.getInstance(requireContext())

        //logIn()
       // signUp()
    }

    private fun logIn() {
        with(binding) {
            etPassword.doAfterTextChanged { text ->
                if (text.toString().validatePassword() && !etLogin.text.isNullOrBlank()) {
                    btnLogIn.isEnabled = true

                    btnLogIn.setOnClickListener {
                        val user =
                            db.userDao()
                                .checkLogIn(etLogin.text.toString(), etPassword.text.toString())
                        if (user != null) {
                            goToFilm()
                        } else {
                            Toast.makeText(activity, "Неверный логин или пароль", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                } else {
                    etPassword.error = "Неверный пароль"
                }
            }
        }
    }

    private fun signUp() {
        setUserImage()

        binding.groupRegistration.isVisible = true
        binding.btnLogIn.isVisible = false

        with(binding) {
            db.userDao().insertUser(User(null, etLogin.text.toString(), etPassword.text.toString()))
        }

        binding.groupRegistration.isVisible = false
        binding.btnLogIn.isVisible = true
    }

    private fun setUserImage() {
        setImagePicker()
        binding.btnSetAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }
    }

    private fun setImagePicker() {
        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val imageUri = result.data?.data
                    Glide.with(this)
                        .load(imageUri)
                        .into(binding.imageUserAvatar)
                }
            }
    }

    private fun goToFilm() {
        val fragment = FilmsFragment()

        fragment.arguments = Bundle().apply {
            putInt(FilmDetailFragment.BUNDLE, id)
        }

        fragmentChangeListener?.replaceFragment(fragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is IFragmentReplace) {
            fragmentChangeListener = context
        } else {
            throw RuntimeException("$context")
        }
    }

    companion object {

        fun buildReg() = RegistrationFragment().apply {
            arguments = bundleOf()
        }

        const val DB_NAME = "user"
    }
}