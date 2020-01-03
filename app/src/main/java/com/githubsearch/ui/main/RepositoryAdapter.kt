package com.githubsearch.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubsearch.R
import com.githubsearch.databinding.ItemRepositoryBinding
import com.githubsearch.model.Repository

class RepositoryAdapter(val listener : ClickListener) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    private var items: ArrayList<Repository> = arrayListOf()

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Repository>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding: ItemRepositoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_repository, parent, false)
        return RepositoryViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(items.get(position))
    }

    class RepositoryViewHolder(val binding: ItemRepositoryBinding, val listener : ClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repository) {
            with(binding) {
                name.text = item.name
                description.text = item.description
                name.text = item.name
                name.text = item.name
                name.text = item.name
                Glide.with(userAvatar).load(item.owner.avatar_url).apply(
                    RequestOptions.circleCropTransform()).into(userAvatar)
                userName.text = item.owner.login
                binding.root.setOnClickListener { listener.onClick(item.html_url) }
            }
        }
    }

    interface ClickListener {
        fun onClick(url : String)
    }
}