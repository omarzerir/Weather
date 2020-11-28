package com.zerir.weather.utilities.bindingAdapters

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("loadPhoto")
fun ImageView.loadPhoto(path: String?) {
    val uri = Uri.parse(path)
    this.setImageURI(uri)
}