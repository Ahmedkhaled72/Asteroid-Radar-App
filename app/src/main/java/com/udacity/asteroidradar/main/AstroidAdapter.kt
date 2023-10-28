package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AstroidItemBinding
import kotlin.contracts.contract

 class AstroidAdapter(private val onClickListener: OnClickListener): ListAdapter<Asteroid, AstroidAdapter.AstroidViewHolder>(DiffCallback)
{
    //ViewHolder take view root and layout
    class AstroidViewHolder(private var binding: AstroidItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(astroid: Asteroid)
        {
            binding.astroid = astroid
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback: DiffUtil.ItemCallback<Asteroid>()
    {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AstroidViewHolder {
          return AstroidViewHolder(AstroidItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AstroidViewHolder, position: Int) {

        val astro = getItem(position)
         holder.bind(astro)
        //click item
        holder.itemView.setOnClickListener{
            onClickListener.onClick(astro)
         }

    }

    class OnClickListener(val clickListener: (astro:Asteroid) -> Unit)
    {
        fun onClick(astro: Asteroid)  = clickListener(astro)//?????
    }


}