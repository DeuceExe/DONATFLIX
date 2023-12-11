package com.example.impl.presentation.fragments.registration

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
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.api.IRegistrationFragment
import com.example.api.IFragmentReplace
import com.example.api.IUserInfo
import com.example.uikit.R
import com.example.impl.databinding.FragmentRegistrationBinding
import com.example.impl.presentation.fragments.film.FilmsFragment
import com.example.impl.room.UserDataBase
import com.example.impl.room.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class RegistrationFragment : Fragment(), IRegistrationFragment, KoinComponent {

    private var _binding: FragmentRegistrationBinding? = null
    val binding get() = requireNotNull(_binding)

    private var fragmentChangeListener: IFragmentReplace? = null
    private lateinit var listener: IUserInfo

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    private lateinit var db: UserDataBase

    private var imagePath = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = UserDataBase.getInstance(requireContext())

        logIn()
        signUp()
    }

    private fun logIn() {
        with(binding) {
            btnLogIn.isEnabled = true

            btnLogIn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val user =
                        db.userDao()
                            .checkLogIn(etLogin.text.toString(), etPassword.text.toString())
                    if (user != null) {
                        goToFilm()
                        user.userImage?.let { image ->
                            sendUserToActivity(user.userLogin,
                                image,
                                listOf())
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            resources.getResourceName(R.string.incorrect_login_pass),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }
    }

    private fun signUp() {
        setUserImage()
        insertUser()

        binding.btnSignUp.setOnClickListener {
            binding.groupRegistration.isVisible = true
            binding.btnLogIn.isVisible = false
            binding.btnSignUp.isEnabled = false
            binding.btnCreateUser.isEnabled = true
        }
    }

    private fun insertUser() {
        binding.btnCreateUser.setOnClickListener {
            with(binding) {
                CoroutineScope(Dispatchers.IO).launch {
                    val user =
                        db.userDao()
                            .checkUserByLogin(etLogin.text.toString())
                    if (user == null) {
                        db.userDao()
                            .insertUser(
                                User(
                                    null,
                                    etLogin.text.toString(),
                                    etPassword.text.toString(),
                                    imagePath
                                )
                            )
                    } else {
                        Toast.makeText(
                            requireContext(),
                            resources.getResourceName(R.string.incorrect_login),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
                groupRegistration.isVisible = false
                btnLogIn.isVisible = true
                btnSignUp.isEnabled = true
                btnCreateUser.isEnabled = false
            }
        }
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
                    imagePath = imageUri.toString()
                }

            }
    }

    private fun goToFilm() {
        val fragment = FilmsFragment()

        fragment.arguments = Bundle()

        fragmentChangeListener?.replaceFragment(fragment)
    }

    private fun sendUserToActivity(login: String, image: String, favoriteFilms: List<Int>) {
        listener.onUserDataReceived(login, image, favoriteFilms)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is IFragmentReplace) {
            listener = context as IUserInfo
            fragmentChangeListener = context
        } else {
            throw RuntimeException("$context")
        }
    }

    companion object {

        fun build() = RegistrationFragment().apply {
            arguments = bundleOf()
        }

        const val DB_NAME = "user"
    }
}