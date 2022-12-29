package com.taichung.bryant.kotlin_mvvm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.taichung.bryant.kotlin_mvvm.R
import com.taichung.bryant.kotlin_mvvm.config.Config.DATA
import com.taichung.bryant.kotlin_mvvm.data.attractions.AttractionsData
import com.taichung.bryant.kotlin_mvvm.databinding.FragmemtAttractionsDetailsBinding
import com.taichung.bryant.kotlin_mvvm.ui.base.BaseFragment
import com.taichung.bryant.kotlin_mvvm.ui.viewmodel.AttractDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttractionsDetailsFragment :
    BaseFragment<FragmemtAttractionsDetailsBinding, AttractDetailsViewModel>(), View.OnClickListener {
    override fun getViewModelClass() = AttractDetailsViewModel::class.java

    override fun getViewBinding() = FragmemtAttractionsDetailsBinding.inflate(layoutInflater)

    private lateinit var data: AttractionsData

    companion object {
        fun newInstance(): AttractionsDetailsFragment {
            val args = Bundle()

            val fragment = AttractionsDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments?.getSerializable(DATA) as AttractionsData
        initToolBar(binding.toolbar, data.name, bBack = true, action = false) {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.data = data
        binding.view = this
        binding.ivBanner.load(data.images.getOrNull(0)?.src) {
            crossfade(true)
            scale(Scale.FILL)
            placeholder(R.drawable.image_defult)
            error(R.drawable.image_defult)
            transformations(RoundedCornersTransformation())
        }
    }

    override fun onClick(v: View?) {
        val bundle = bundleOf("url" to data.url)
        findNavController().navigate(R.id.action_attractionsDetailsFragment_to_attractionsDetailWebViewFragment, bundle)
    }
}
