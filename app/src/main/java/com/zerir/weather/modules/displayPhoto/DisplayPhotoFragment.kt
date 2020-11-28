package com.zerir.weather.modules.displayPhoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.zerir.weather.R
import com.zerir.weather.databinding.FragmentDisplayPhotoBinding
import com.zerir.weather.utilities.shareImage

class DisplayPhotoFragment : Fragment() {

    private lateinit var binding: FragmentDisplayPhotoBinding

    private var path: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_display_photo, container, false)

        arguments?.let {
            val args = DisplayPhotoFragmentArgs.fromBundle(it)
            path = args.path
            binding.path = path
        }

        binding.shareTv.setOnClickListener {
            shareImage(this, path)
        }

        return binding.root
    }
}