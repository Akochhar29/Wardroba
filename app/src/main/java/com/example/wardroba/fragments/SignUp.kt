package com.example.wardroba.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wardroba.databinding.FragmentSignUpBinding
import com.example.wardroba.models.UserAuth
import com.example.wardroba.models.User
import com.example.wardroba.vms.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

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
            if(!TextUtils.isEmpty(binding.etEmail.text) && android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text).matches()
                && !TextUtils.isEmpty(binding.etFirstName.text)&& !TextUtils.isEmpty(binding.etLastName.text)&& !TextUtils.isEmpty(binding.etPass.text)
                && binding.etPass.text.toString().equals(binding.etPassConf.text.toString())){

                val createdUserData = User( firstName = binding.etFirstName.text.toString(), lastName = binding.etLastName.text.toString())
                val createdUserAuth = UserAuth(email = binding.etEmail.text.toString(),password = binding.etPass.text.toString())
                userModel.addUser(createdUserData,createdUserAuth)
                val action = SignUpDirections.actionSignUpToHome22()
                findNavController().navigate(action)
            }


        }
    }
}