package com.oguzel.travel_app.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.FragmentDetailBinding
import com.oguzel.travel_app.databinding.FragmentLoginBinding


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
        auth = FirebaseAuth.getInstance()

        init()

    }

    private fun init(){

        binding.apply {
            buttonLogin.setOnClickListener {
                val email = editTextEmailLogin.text.toString()
                if (email.isEmpty()) {
                    editTextEmailLogin.error = "please enter your email"
                }
                val password = editTextPasswordLogin.text.toString()
                if (password.isEmpty()) {
                    editTextPasswordLogin.error = "please enter your password"
                }
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            println("successful")
                            val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            findNavController().navigate(action)
                        }
                    }.addOnFailureListener {
                        println("not successful")
                        println(it)
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
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