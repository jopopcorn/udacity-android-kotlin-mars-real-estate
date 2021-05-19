package com.example.android.marsrealestate.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.marsrealestate.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        val marsProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        val viewModelFactory = DetailViewModelFactory(marsProperty, application)
        binding.viewModel = ViewModelProvider(
                this, viewModelFactory).get(DetailViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }
}
