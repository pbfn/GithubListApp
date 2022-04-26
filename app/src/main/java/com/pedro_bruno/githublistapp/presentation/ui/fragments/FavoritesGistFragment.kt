package com.pedro_bruno.githublistapp.presentation.ui.fragments

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pedro_bruno.githublistapp.R
import com.pedro_bruno.githublistapp.databinding.FragmentFavoritesGistBinding
import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.presentation.adapters.AdapterGist
import com.pedro_bruno.githublistapp.presentation.viewmodel.FavoriteViewModel
import com.pedro_bruno.githublistapp.util.ViewState
import org.koin.androidx.viewmodel.ext.android.getViewModel


class FavoritesGistFragment() : Fragment() {

    private var _binding: FragmentFavoritesGistBinding? = null
    private val binding: FragmentFavoritesGistBinding get() = _binding!!

    private lateinit var adapterGist: AdapterGist
    private var indexRemove: Int = 0

    private val favoriteGistViewModel: FavoriteViewModel by lazy {
        getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoritesGistBinding.inflate(inflater, container, false).apply {
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
        favoriteGistViewModel.fetchLocalGistList("")
        setupDeleteMove()
        setupListener()
    }

    private fun observeData() {
        favoriteGistViewModel.gistList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Success -> {
                    val listGist = response.data
                    if (listGist.isEmpty()) {
                        binding.apply {
                            tvEmptyList.apply {
                                visibility = View.VISIBLE
                                text = getString(R.string.empty_list)
                            }
                        }
                    } else {
                        binding.tvEmptyList.visibility = View.INVISIBLE
                    }
                    adapterGist.differ.submitList(listGist)
                }
                is ViewState.Error -> {
                    binding.apply {
                        tvEmptyList.apply {
                            visibility = View.VISIBLE
                            text = getString(R.string.failed_bd)
                        }
                    }
                }
            }
        }

        favoriteGistViewModel.removeGistResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Success -> {
                    val gist = response.data
                    val list: MutableList<Gist> = mutableListOf()
                    list.addAll(adapterGist.differ.currentList)
                    indexRemove = adapterGist.differ.currentList.indexOf(gist)
                    list.remove(gist)
                    adapterGist.differ.submitList(list)
                }
            }
        }

        favoriteGistViewModel.favoriteGistResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Success -> {
                    val gist = response.data
                    gist.checked = true
                    val list: MutableList<Gist> = mutableListOf()
                    list.addAll(adapterGist.differ.currentList)
                    list.add(indexRemove, gist)
                    adapterGist.differ.submitList(list)
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
            nextPage(
                FavoritesGistFragmentDirections.actionFavoritesGistFragmentToDetailsFragment(
                    gist
                )
            )
        }
    }

    private fun setupFavoriteItem() {
        adapterGist.setOnFavClickListener { gistClicked ->
            favoriteGistViewModel.removeGistFromfavorite(gistClicked)
            view?.let {
                Snackbar.make(it, "Successfully deleted gist", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        favoriteGistViewModel.favoriteGist(gistClicked)
                    }
                }.show()
            }
        }
    }

    private fun nextPage(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    private fun setupDeleteMove() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gist = adapterGist.differ.currentList[position]
                favoriteGistViewModel.removeGistFromfavorite(gist)
                view?.let {
                    Snackbar.make(it, "Successfully deleted gist", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            favoriteGistViewModel.favoriteGist(gist)
                        }
                    }.show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvGist)
        }
    }

    private fun setupListener() {
        binding.apply {
            editTextSearch.doOnTextChanged { text, _, _, _ ->
                favoriteGistViewModel.fetchLocalGistList(search = text.toString())
            }
        }
    }
}
