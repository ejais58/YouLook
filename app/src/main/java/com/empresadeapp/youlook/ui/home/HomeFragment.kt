package com.empresadeapp.youlook.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.empresadeapp.youlook.R
import com.empresadeapp.youlook.databinding.FragmentHomeBinding



class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
    }

}