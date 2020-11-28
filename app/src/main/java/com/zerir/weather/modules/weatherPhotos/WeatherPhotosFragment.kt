package com.zerir.weather.modules.weatherPhotos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.zerir.weather.R
import com.zerir.weather.databinding.FragmentWeatherPhotosBinding

class WeatherPhotosFragment : Fragment() {

    private lateinit var binding: FragmentWeatherPhotosBinding
    private lateinit var viewModel: WeatherPhotosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_photos, container, false)

        binding.addPhotoFab.setOnClickListener {
            val action = WeatherPhotosFragmentDirections.actionWeatherPhotosFragmentToAddWeatherPhotoFragment()
            Navigation.findNavController(binding.root).navigate(action)
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
                if(list.isEmpty()){
                    binding.noPhotosTv.visibility = VISIBLE
                    binding.photosRv.visibility = GONE
                }else {
                    viewModel.weatherPhotosAdapter.submitList(list)
                    binding.noPhotosTv.visibility = GONE
                    binding.photosRv.visibility = VISIBLE
                }
            }
        })

        viewModel.displayPhoto.observe(viewLifecycleOwner, { path ->
            path?.let {
                val action = WeatherPhotosFragmentDirections.
                actionWeatherPhotosFragmentToDisplayPhotoFragment(it)
                Navigation.findNavController(requireActivity(), R.id.main_nav_host_fragment)
                    .navigate(action)
                viewModel.clearDisplayPhoto()
            }
        })
    }

}