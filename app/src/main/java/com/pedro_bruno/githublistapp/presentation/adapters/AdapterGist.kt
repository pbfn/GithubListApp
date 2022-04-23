package com.pedro_bruno.githublistapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pedro_bruno.githublistapp.databinding.ItemGistAdapterBinding
import com.pedro_bruno.githublistapp.domain.model.Gist

class AdapterGist() : RecyclerView.Adapter<AdapterGist.AdapterGistViewHolder>() {

    class AdapterGistViewHolder(itemView: ItemGistAdapterBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val image = itemView.imageGist
        val nameOwner = itemView.tvNameOwnerGist
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
        return AdapterGistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterGistViewHolder, position: Int) {
        val gist = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load(gist.photoOwen).into(holder.image)
        }
        holder.apply {
            nameOwner.text = gist.nameOwner
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}