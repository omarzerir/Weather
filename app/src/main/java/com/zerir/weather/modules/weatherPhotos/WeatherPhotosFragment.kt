package com.zerir.weather.modules.weatherPhotos

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.zerir.weather.R
import com.zerir.weather.databinding.FragmentWeatherPhotosBinding
import com.zerir.weather.utilities.ALL_PERMISSIONS_TAG
import com.zerir.weather.utilities.checkAllPermission
import com.zerir.weather.utilities.dialogs.ProviderDialog
import com.zerir.weather.utilities.requestAllPermissions

class WeatherPhotosFragment : Fragment() {

    private lateinit var binding: FragmentWeatherPhotosBinding
    private lateinit var viewModel: WeatherPhotosViewModel

    private lateinit var providerDialog: ProviderDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_weather_photos, container, false)

        providerDialog = ProviderDialog(requireActivity(), object: ProviderDialog.OnProviderDialogDone{
            override fun onEnableGps() {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        })

        binding.addPhotoFab.setOnClickListener {
            if (checkAllPermission(this)) {
                viewModel.requestLocation(requireActivity())
            } else {
                requestAllPermissions(this)
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(WeatherPhotosViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.photos.observe(viewLifecycleOwner, { list ->
            list?.let {
                Log.e("LIST", list.toString())
                if (list.isEmpty()) {
                    binding.noPhotosTv.visibility = VISIBLE
                    binding.photosRv.visibility = GONE
                } else {
                    viewModel.weatherPhotosAdapter.submitList(list)
                    binding.noPhotosTv.visibility = GONE
                    binding.photosRv.visibility = VISIBLE
                }
            }
        })

        viewModel.displayPhoto.observe(viewLifecycleOwner, { path ->
            path?.let {
                val action =
                    WeatherPhotosFragmentDirections.actionWeatherPhotosFragmentToDisplayPhotoFragment(
                        it
                    )
                Navigation.findNavController(requireActivity(), R.id.main_nav_host_fragment)
                    .navigate(action)
                viewModel.clearDisplayPhoto()
            }
        })

        viewModel.latLon.observe(viewLifecycleOwner, { latLon ->
            latLon?.let {
                val action =
                    WeatherPhotosFragmentDirections.actionWeatherPhotosFragmentToAddWeatherPhotoFragment(it)
                Navigation.findNavController(binding.root).navigate(action)
                viewModel.clearLatLon()
            }
        })

        viewModel.provider.observe(viewLifecycleOwner, { isProviderDisable ->
            isProviderDisable?.let {
                if (it) {
                    providerDialog.show()
                    viewModel.clearProvider()
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                ALL_PERMISSIONS_TAG -> {
                    if(checkAllPermission(this)) {

                        viewModel.requestLocation(requireActivity())

                    } else {
                        Toast.makeText(
                            requireActivity(), "You should allow all permissions to add Photo",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

}