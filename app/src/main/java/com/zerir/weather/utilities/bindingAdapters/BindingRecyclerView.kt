package com.zerir.weather.utilities.bindingAdapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("setupAdapter")
fun RecyclerView.setupAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) {
    this.adapter = adapter
}