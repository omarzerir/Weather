package com.zerir.weather.modules.weatherPhotos.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zerir.weather.databinding.RowWeatherPhotoItemBinding

class WeatherPhotosAdapter(private val onPhotoClickListener: OnPhotoClickListener)
    : ListAdapter<String, RecyclerView.ViewHolder>(WeatherPhotosDiffCallback()) {

    interface OnPhotoClickListener {
        fun onPhotoClicked(path: String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val data = getItem(position)
                holder.bind(data, onPhotoClickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: RowWeatherPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowWeatherPhotoItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(path: String, onPhotoClickListener: OnPhotoClickListener) {
            binding.path = path
            binding.listener = onPhotoClickListener
        }
    }
}

class WeatherPhotosDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }
}