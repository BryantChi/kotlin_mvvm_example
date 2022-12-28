package com.taichung.bryant.kotlin_mvvm.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.taichung.bryant.kotlin_mvvm.R
import com.taichung.bryant.kotlin_mvvm.config.Config
import com.taichung.bryant.kotlin_mvvm.data.attractions.AttractionsData
import com.taichung.bryant.kotlin_mvvm.databinding.FragmemtAttractionsDetailsBinding
import com.taichung.bryant.kotlin_mvvm.ui.base.BaseFragment
import com.taichung.bryant.kotlin_mvvm.ui.viewmodel.AttractDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttractionsDetailsFragment :
    BaseFragment<FragmemtAttractionsDetailsBinding, AttractDetailsViewModel>() {
    override fun getViewModelClass() = AttractDetailsViewModel::class.java

    override fun getViewBinding() = FragmemtAttractionsDetailsBinding.inflate(layoutInflater)

    lateinit var data: AttractionsData

    companion object {
        fun newInstance(): AttractionsDetailsFragment {
            val args = Bundle()

            val fragment = AttractionsDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = arguments?.getSerializable(Config.DATA) as AttractionsData
        binding.data = data
        binding.ivBanner.load(data.images.getOrNull(0)?.src) {
            crossfade(true)
            scale(Scale.FILL)
            placeholder(R.drawable.image_defult)
            error(R.drawable.image_defult)
            transformations(RoundedCornersTransformation())
        }
//        viewModel.url.observe(viewLifecycleOwner) {
//            val bundle = bundleOf("url" to it)
//            findNavController().navigate(R.id.AttractionsDetailWebviewFragment, bundle)
//        }
        binding.tvUrl.setOnClickListener {
            val bundle = bundleOf("url" to data.url)
            findNavController().navigate(R.id.AttractionsDetailWebviewFragment, bundle)
        }

        binding.toolbar.title = data.name
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back)

        binding.toolbar.setNavigationOnClickListener { _ ->
            findNavController().popBackStack()
        }
    }


}
