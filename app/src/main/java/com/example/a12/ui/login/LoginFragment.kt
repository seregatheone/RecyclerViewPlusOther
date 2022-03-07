package com.example.a12.ui.login

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a12.R
import com.example.a12.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferencesUID: SharedPreferences
    override fun onAttach(activity: Activity){
        super.onAttach(activity)
        sharedPreferencesUID = activity.application.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        auth = Firebase.auth
        Log.i("sharedPreferencesUID",sharedPreferencesUID.getString("uid","null").toString())
        if(sharedPreferencesUID.getString("uid","null").toString()!="null"){
            auth.signInWithCustomToken(sharedPreferencesUID.getString("uid","null").toString())
            findNavController().navigate(R.id.action_navigation_login_to_userProfileFragment)
            }
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonLog.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_login_to_registrationFragment)
        }
        binding.logInButt.setOnClickListener {
            userLogIn()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun userLogIn() {
        val email = binding.editTextEmailLog.text.toString()
        val pass = binding.editTextTextPasswordLog.text.toString()
        if (email.isEmpty()){
            binding.editTextEmailLog.error = "Provide valid email"
            binding.editTextEmailLog.requestFocus()
        }
        if (pass.isEmpty()){
            binding.editTextTextPasswordLog.error = "Provide valid password"
            binding.editTextTextPasswordLog.requestFocus()
        }
        if (pass.length<6){
            binding.editTextTextPasswordLog.error = "Password length must be bigger than 6"
            binding.editTextTextPasswordLog.requestFocus()
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editTextEmailLog.error = "Provide valid email"
            binding.editTextEmailLog.requestFocus()
        }
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sharedPreferencesUID.edit().putString("uid", auth.currentUser!!.uid)
                    findNavController().navigate(R.id.action_navigation_login_to_userProfileFragment)
                } else {
                    Toast.makeText(context, "write correct info", Toast.LENGTH_LONG).show()
                }
            }

            .addOnFailureListener {
                Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}