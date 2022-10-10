package com.oguzel.travel_app.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.oguzel.travel_app.R
import com.oguzel.travel_app.data.local.entity.User
import com.oguzel.travel_app.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mFireStore: FirebaseFirestore
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    /**
     * For register function Firebase is being used.
     */
    private fun init() {
        auth = FirebaseAuth.getInstance()
        mFireStore = FirebaseFirestore.getInstance()
        binding.apply {
            buttonRegister.setOnClickListener {
                val email = editTextEmailRegister.text.toString()
                if (email.isEmpty()) {
                    editTextEmailRegister.error = "Please enter your email"
                }
                val password = editTextPasswordRegister.text.toString()
                if (password.isEmpty()) {
                    editTextPasswordRegister.error = "Please enter your password"
                }
                val name = editTextFullNameRegister.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val firebaseUser: FirebaseUser = it.result!!.user!!
                            val user =
                                User(firebaseUser.uid, name, email)
                            viewModel.saveUserToFirebase(user)
                            val action =
                                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                            findNavController().navigate(action)
                        }
                    }.addOnFailureListener {
                        println("Firebase register error : ${it.message}")
                    }
                }
            }
        }
    }
}