package com.example.wardroba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.wardroba.R
import com.example.wardroba.databinding.FragmentHomeBinding
import com.example.wardroba.databinding.FragmentSignInBinding
import com.example.wardroba.vms.UserViewModel

class SignIn : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val userModel: UserViewModel by activityViewModels<UserViewModel>()
    private val loginObserver= Observer<Boolean> {
        if(it == true){
            val action = SignInDirections.actionSignInToHome22()
            findNavController().navigate(action)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        userModel.loginSuccessful.observe(viewLifecycleOwner,loginObserver)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            userModel.login(binding.etEmail.text.toString(),binding.etPass.text.toString())

        }
    }
}