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
import com.zerir.weather.repository.data.weather.Weather
import com.zerir.weather.repository.remote.retrofit.ApiStatusType
import com.zerir.weather.utilities.*
import com.zerir.weather.utilities.dialogs.ErrorDialog
import com.zerir.weather.utilities.dialogs.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWeatherPhotoFragment : Fragment() {

    private lateinit var binding: FragmentAddWeatherPhotoBinding
    private lateinit var viewModel: AddWeatherPhotoViewModel

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var errorDialog: ErrorDialog

    private var photoBitmap: Bitmap? = null

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

        loadingDialog = LoadingDialog(requireActivity())
        errorDialog = ErrorDialog(requireActivity(), object: ErrorDialog.OnErrorDialogDone {
            override fun onErrorDone() {
                requireActivity().onBackPressed()
            }
        })

        if (checkStoragePermission(this)) openCamera(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddWeatherPhotoViewModel::class.java)

        viewModel.weatherStatus.observe(viewLifecycleOwner, { status ->
            status?.let {
                when(status){
                    ApiStatusType.LOADING -> {
                        loadingDialog.show()
                    }
                    ApiStatusType.SUCCESS -> {
                        loadingDialog.dismiss()
                        val weather = it.data as Weather
                        viewModel.onWeatherDataGot(photoBitmap!!, weather)
                    }
                    ApiStatusType.FAILURE -> {
                        loadingDialog.dismiss()
                        val message = it.data as String
                        errorDialog.show(message)
                    }
                }
                viewModel.clearWeatherStatus()
            }
        })

        viewModel.finalPhoto.observe(viewLifecycleOwner, {
            it?.let { path ->
                val uri = Uri.parse(path)
                binding.weatherPhotoIv.setImageURI(uri)

                binding.shareTv.visibility = VISIBLE
                binding.shareTv.setOnClickListener {
                    shareImage(this, path)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                TAKE_PHOTO_TAG -> {
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            photoBitmap = data.extras!!["data"] as Bitmap?
                            binding.weatherPhotoIv.setImageBitmap(photoBitmap)
                            viewModel.getWeatherData("Istanbul")
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