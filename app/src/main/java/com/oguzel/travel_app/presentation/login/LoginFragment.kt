package com.oguzel.travel_app.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    /**
     * For login function Firebase is being used.
     */
    private fun init() {
        auth = FirebaseAuth.getInstance()
        binding.apply {
            buttonLogin.setOnClickListener {
                val email = editTextEmailLogin.text.toString()
                if (email.isEmpty()) {
                    editTextEmailLogin.error = "Please enter your email"
                }
                val password = editTextPasswordLogin.text.toString()
                if (password.isEmpty()) {
                    editTextPasswordLogin.error = "Please enter your password"
                }
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            findNavController().navigate(action)
                        }
                    }.addOnFailureListener {
                        println("Firebase error : ${it.message}")
                    }
                }
            }

            textViewSignupNavigate.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}