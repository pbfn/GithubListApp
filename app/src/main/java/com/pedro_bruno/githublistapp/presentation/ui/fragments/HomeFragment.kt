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
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedro_bruno.githublistapp.R
import com.pedro_bruno.githublistapp.databinding.FragmentHomeBinding
import com.pedro_bruno.githublistapp.presentation.adapters.AdapterGist
import com.pedro_bruno.githublistapp.presentation.viewmodel.HomeViewModel
import com.pedro_bruno.githublistapp.util.ViewState
import org.koin.androidx.viewmodel.ext.android.getViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val homeViewModel: HomeViewModel by lazy {
        getViewModel()
    }
    private lateinit var adapterGist: AdapterGist

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupRecyclerView()
    }

    private fun observeData() {
        homeViewModel.gistList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Success -> {
                    adapterGist.differ.submitList(response.data)
                }
                is ViewState.Error -> {

                }
            }
        }
    }

    private fun setupRecyclerView() {
        val layout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapterGist = AdapterGist()
        setupClickItem()
        setupFavoriteItem()
        binding.rvGist.apply {
            layoutManager = layout
            adapter = adapterGist
        }
    }

    private fun setupClickItem() {
        adapterGist.setOnItemClickListener { gist ->
            nextPage(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(gist))
        }
    }

    private fun setupFavoriteItem() {
        adapterGist.setOnFavClickListener { gist ->
            //TODO SALVAR ITEM
        }
    }

    private fun nextPage(directions: NavDirections) {
        findNavController().navigate(directions)
    }
}