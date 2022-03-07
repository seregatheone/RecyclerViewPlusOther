package com.example.a12.ui.registration

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a12.R
import com.example.a12.User
import com.example.a12.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_navigation_login)
        }
        binding.regbut.setOnClickListener {
            registration()
            findNavController().navigate(R.id.action_registrationFragment_to_navigation_login)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun registration() {
        val email = binding.editTextTextEmail.text.toString()
        val phone = binding.editTextPhone.text.toString()
        val password = binding.editTextTextPassword.text.toString()
        if (email.isEmpty()){
            binding.editTextTextEmail.error = "Provide valid email"
            binding.editTextTextEmail.requestFocus()
        }
        if (password.isEmpty()){
            binding.editTextTextPassword.error = "Provide valid password"
            binding.editTextTextPassword.requestFocus()
        }
        if (password.length<6){
            binding.editTextTextPassword.error = "Password length must be bigger than 6"
            binding.editTextTextPassword.requestFocus()
        }
        if (phone.isEmpty()){
            binding.editTextPhone.error = "Provide valid phone number"
            binding.editTextPhone.requestFocus()
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editTextTextEmail.error = "Provide valid email"
            binding.editTextTextEmail.requestFocus()
        }
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { userCreated ->
                if (userCreated.isSuccessful) {
                    val user = User(email = email, phone = phone, password = password)
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(user).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(context, "User has been added", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(context, "2", Toast.LENGTH_LONG).show()
                            }
                        }
                }else{
                    Toast.makeText(context,"введите коректные данные", Toast.LENGTH_LONG).show()
                    throw Exception(userCreated.exception.toString())
                }
            }
    }

}