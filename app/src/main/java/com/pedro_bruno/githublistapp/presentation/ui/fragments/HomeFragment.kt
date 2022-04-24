package com.pedro_bruno.githublistapp.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pedro_bruno.githublistapp.R
import com.pedro_bruno.githublistapp.databinding.FragmentHomeBinding
import com.pedro_bruno.githublistapp.domain.exceptions.LimitResquestException
import com.pedro_bruno.githublistapp.presentation.adapters.AdapterGist
import com.pedro_bruno.githublistapp.presentation.viewmodel.HomeViewModel
import com.pedro_bruno.githublistapp.util.Constants.QUERY_PAGE_SIZE
import com.pedro_bruno.githublistapp.util.ViewState
import org.koin.androidx.viewmodel.ext.android.getViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val homeViewModel: HomeViewModel by lazy {
        getViewModel()
    }
    private lateinit var adapterGist: AdapterGist

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible

            if (shouldPaginate) {
                homeViewModel.fetchRemoteGistList(adapterGist.differ.currentList)
                isScrolling = false
            }
        }
    }

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
                    adapterGist.differ.submitList(response.data.toList())
                }
                is ViewState.Error -> {
                    when (response.throwable) {
                        is LimitResquestException -> {
                            binding.apply {
                                if (adapterGist.itemCount == 0) {
                                    tvEmptyList.visibility = View.VISIBLE
                                }
                            }
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.erro_limit_request),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        homeViewModel.showProgressBar.observe(viewLifecycleOwner) { showProgress ->
            changeVisibilityProgress(showProgress)
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
            addOnScrollListener(this@HomeFragment.scrollListener)
        }
    }

    private fun setupClickItem() {
        adapterGist.setOnItemClickListener { gist ->
            nextPage(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(gist))
        }
    }

    private fun setupFavoriteItem() {
        adapterGist.setOnFavClickListener { gist ->
            if (gist.checked) {
                homeViewModel.favoriteGist(gist)
            } else {
                homeViewModel.removeGistFromfavorite(gist)
            }
        }
    }

    private fun nextPage(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    private fun changeVisibilityProgress(loading: Boolean) {
        binding.progressBar.visibility = if (loading) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
        isLoading = loading
    }
}