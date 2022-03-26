package com.example.wardroba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wardroba.R
import com.example.wardroba.databinding.FragmentHomeBinding
import com.example.wardroba.databinding.FragmentSignInBinding
import com.example.wardroba.databinding.FragmentSignUpBinding
import com.example.wardroba.models.UserAuth
import com.example.wardroba.models.UserData
import com.example.wardroba.vms.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUp : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val userModel: UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            val createdUserData = UserData( firstName = binding.etFirstName.text.toString(), lastName = binding.etLastName.text.toString())
            val createdUserAuth = UserAuth(email = binding.etEmail.text.toString(),password = binding.etPass.text.toString())
            userModel.addUser(createdUserData,createdUserAuth)
            val action = SignUpDirections.actionSignUpToHome22()
            findNavController().navigate(action)

        }
    }
}