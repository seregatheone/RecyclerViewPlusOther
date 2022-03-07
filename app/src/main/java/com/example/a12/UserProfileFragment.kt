package com.example.a12

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a12.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var state:SharedPreferences

    private lateinit var auth: FirebaseAuth

    override fun onAttach(activity: Activity) {
        state = activity.application.getSharedPreferences("settings", Context.MODE_PRIVATE)
        super.onAttach(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        _binding = FragmentUserProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Button logout
        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val editor = state.edit()
            editor!!.putString("uid", auth.uid.toString())
            editor.apply()
            Log.i("sharedPreferencesUID",state.getString("uid","null").toString())
            findNavController().navigate(R.id.action_userProfileFragment_to_registrationFragment)
        }

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val email = user.email
            binding.email.text = email
        }

        super.onViewCreated(view, savedInstanceState)
    }
}