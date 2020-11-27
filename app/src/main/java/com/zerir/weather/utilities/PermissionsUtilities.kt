package com.zerir.weather.utilities

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

const val STORAGE_PERMISSION_TAG = 202

fun checkStoragePermission(context: Fragment): Boolean {
    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val result = ActivityCompat.checkSelfPermission(
        context.requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) != PackageManager.PERMISSION_GRANTED
    return if (result) {
        context.requestPermissions(permissions, STORAGE_PERMISSION_TAG)
        false
    } else true
}