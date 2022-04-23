package com.pedro_bruno.githublistapp.presentation.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.pedro_bruno.githublistapp.R
import com.pedro_bruno.githublistapp.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSplashBinding.inflate(layoutInflater, container, false).apply {
        _binding = this
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun nextPage(directions: NavDirections) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            findNavController().navigate(directions)
        }, 5000)
    }

}