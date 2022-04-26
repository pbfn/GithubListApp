package com.pedro_bruno.githublistapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.pedro_bruno.githublistapp.databinding.ItemGistAdapterBinding
import com.pedro_bruno.githublistapp.domain.model.Gist

class AdapterGist() : RecyclerView.Adapter<AdapterGist.AdapterGistViewHolder>() {

    private lateinit var context: Context

    class AdapterGistViewHolder(itemView: ItemGistAdapterBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val image = itemView.imageGist
        val nameOwner = itemView.tvNameOwnerGist
        val cardGist = itemView.cardGist
        val btnFav = itemView.btnFav
        val chipGroupTypes = itemView.chipGroupTypes
    }

    private val differCallback = object : DiffUtil.ItemCallback<Gist>() {
        override fun areItemsTheSame(oldItem: Gist, newItem: Gist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Gist, newItem: Gist): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterGistViewHolder {
        val binding = ItemGistAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return AdapterGistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterGistViewHolder, position: Int) {
        val gist = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(gist.photoOwen).into(holder.image)
        }
        holder.apply {
            nameOwner.text = gist.nameOwner
            cardGist.setOnClickListener {
                onItemClickListener?.let {
                    it(gist)
                }
            }
            var limit = 2
            for (type in gist.gistType) {
                chipGroupTypes.removeAllViews()
                while (limit != 0) {
                    val chip = Chip(context)
                    chip.text = type.type
                    chip.isCheckable = false
                    chipGroupTypes.addView(chip)
                    limit--
                }
                break
            }
            btnFav.isChecked = gist.checked
            btnFav.setOnClickListener {
                gist.checked = btnFav.isChecked
                onFavClickListener?.let {
                    it(gist)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Gist) -> Unit)? = null
    private var onFavClickListener: ((Gist) -> Unit)? = null

    fun setOnItemClickListener(listener: (Gist) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnFavClickListener(listener: (Gist) -> Unit) {
        onFavClickListener = listener
    }
}