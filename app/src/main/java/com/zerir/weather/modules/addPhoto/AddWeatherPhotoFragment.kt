package com.zerir.weather.modules.addPhoto

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zerir.weather.R
import com.zerir.weather.databinding.FragmentAddWeatherPhotoBinding
import com.zerir.weather.utilities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWeatherPhotoFragment : Fragment() {

    private lateinit var binding: FragmentAddWeatherPhotoBinding
    private lateinit var viewModel: AddWeatherPhotoViewModel

    private var photoPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_weather_photo,
            container,
            false
        )

        if (checkStoragePermission(this)) openCamera(this)

        binding.shareTv.setOnClickListener {
            shareImage(this, photoPath)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddWeatherPhotoViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                TAKE_PHOTO_TAG -> {
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val selectedImage = data.extras!!["data"] as Bitmap?
                            photoPath = drawMultilineTextToBitmap(
                                this@AddWeatherPhotoFragment,
                                selectedImage,
                                "Weather Desc: 8, Istanbul, hum. 25% Weather Desc: 8, Istanbul, hum. 25% Weather Desc: 8, Istanbul, hum. 25%"
                            )
                            val uri = Uri.parse(photoPath)
                            binding.weatherPhotoIv.setImageURI(uri)
                            binding.shareTv.visibility = VISIBLE
                        }
                    }
                }
            }
        } else {
            requireActivity().onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                STORAGE_PERMISSION_TAG -> {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openCamera(this)
                    } else {
                        requireActivity().onBackPressed()
                    }
                }
            }
        }
    }

}