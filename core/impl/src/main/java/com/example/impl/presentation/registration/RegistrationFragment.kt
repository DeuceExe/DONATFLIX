package com.example.impl.presentation.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.api.IFilmFragment
import com.example.api.IFilmFragmentReplace
import com.example.impl.databinding.FragmentRegistrationBinding
import java.util.regex.Pattern

fun String.validatePassword(): Boolean = Pattern.compile(PASSWORD_PATTERN).matcher(this).matches()
const val PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"

class RegistrationFragment : Fragment(), IFilmFragment {

    private var _binding: FragmentRegistrationBinding? = null
    val binding get() = requireNotNull(_binding)

    private var fragmentChangeListener: IFilmFragmentReplace? = null

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

    }

    private fun logIn(){
    }

    private fun signUp(){
        binding.etPass.doAfterTextChanged { text ->
            if(text.toString().validatePassword() && !binding.etLogin.text.isNullOrBlank()){
                binding.btnLogIn.isEnabled = true
                binding.btnLogIn.setOnClickListener {
                    //nextScreen
                }
            } else {
               binding.etPass.error = "Неверный пароль"
            }
        }
    }
}