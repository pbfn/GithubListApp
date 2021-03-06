package com.pedro_bruno.githublistapp.presentation.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AbsListView
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pedro_bruno.githublistapp.R
import com.pedro_bruno.githublistapp.databinding.FragmentHomeBinding
import com.pedro_bruno.githublistapp.domain.exceptions.GenericRequestException
import com.pedro_bruno.githublistapp.domain.exceptions.LimitRequestException
import com.pedro_bruno.githublistapp.extensions.hasInternet
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
                if (this@HomeFragment.hasInternet()) {
                    val search = binding.editTextSearch.text.toString()
                    if (search.isNotEmpty()) {
                        homeViewModel.searchGistList(
                            oldGistList = adapterGist.differ.currentList,
                            owner = search
                        )
                    } else {
                        homeViewModel.fetchRemoteGistList(oldGistList = adapterGist.differ.currentList)
                    }
                    isScrolling = false
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Sem conex??o com a internet",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).apply {
        _binding = this
        setHasOptionsMenu(true)
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupRecyclerView()
        testConnection()
        setupListener()
    }

    private fun testConnection() {
        if (this.hasInternet()) {
            binding.tvEmptyList.visibility = View.GONE
            if (homeViewModel.gistList.value is ViewState.Success) {
                val state = homeViewModel.gistList.value as ViewState.Success
                if (state.data.isEmpty()) {
                    binding.apply {
                        tvEmptyList.visibility = View.VISIBLE
                        tvEmptyList.text = getString(R.string.empty_list)
                        rvGist.visibility = View.INVISIBLE
                    }
                } else {
                    binding.apply {
                        tvEmptyList.visibility = View.GONE
                        rvGist.visibility = View.VISIBLE
                    }
                    adapterGist.differ.submitList(state.data)
                }

            } else {
                homeViewModel.fetchRemoteGistList()
            }

        } else {
            binding.apply {
                if (homeViewModel.gistList.value is ViewState.Success) {
                    val state = homeViewModel.gistList.value as ViewState.Success
                    adapterGist.differ.submitList(state.data)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.network_failed),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    tvEmptyList.visibility = View.VISIBLE
                    tvEmptyList.text = getString(R.string.network_failed)
                }
            }


        }
    }

    private fun observeData() {
        homeViewModel.gistList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Success -> {
                    val listResponse = response.data
                    if (listResponse.isEmpty()) {
                        binding.apply {
                            tvEmptyList.visibility = View.VISIBLE
                            tvEmptyList.text = getString(R.string.empty_list)
                            rvGist.visibility = View.INVISIBLE
                        }
                    } else {
                        binding.apply {
                            tvEmptyList.visibility = View.GONE
                            rvGist.visibility = View.VISIBLE
                        }
                        adapterGist.differ.submitList(response.data)
                    }

                }
                is ViewState.Error -> {
                    when (response.throwable) {
                        is LimitRequestException -> {
                            showError(getString(R.string.erro_limit_request))
                        }
                        is GenericRequestException -> {
                            showError(getString(R.string.failed_request))
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
                homeViewModel.removeGistFavorite(gist)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_sincronize -> {
            testConnection()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupListener() {
        //USADO O CLICK AO INVES DO TEXT CHANGE LISTENER PARA QUEBRAR O LIMITE DE REQ
        binding.apply {
            textInputSearch.setEndIconOnClickListener {
                val search = editTextSearch.text.toString()
                if (search.isNotEmpty()) {
                    homeViewModel.searchGistList(owner = search)
                } else {
                    homeViewModel.fetchRemoteGistList()
                }

            }
        }
    }

    private fun showError(msg: String) {
        binding.apply {
            if (adapterGist.itemCount == 0 || !editTextSearch.text.isNullOrEmpty()) {
                tvEmptyList.apply {
                    visibility = View.VISIBLE
                    text = msg
                }
                rvGist.visibility = View.INVISIBLE
            } else {
                Toast.makeText(
                    requireContext(),
                    msg,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}