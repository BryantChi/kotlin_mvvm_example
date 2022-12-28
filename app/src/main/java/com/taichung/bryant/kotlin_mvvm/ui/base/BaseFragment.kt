package com.taichung.bryant.kotlin_mvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.taichung.bryant.kotlin_mvvm.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<VBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {

    open var useSharedViewModel: Boolean = false

    protected lateinit var viewModel: ViewModel
    protected abstract fun getViewModelClass(): Class<ViewModel>

    protected lateinit var binding: VBinding
    protected abstract fun getViewBinding(): VBinding

    private val disposableContainer = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initToolBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeData()
    }

    open fun setUpViews() {}

    open fun observeView() {}

    open fun observeData() {}

    private fun init() {
        binding = getViewBinding()
        viewModel = if (useSharedViewModel) {
            ViewModelProvider(requireActivity()).get(
                getViewModelClass()
            )
        } else {
            ViewModelProvider(this).get(getViewModelClass())
        }
    }

    fun initToolBar(
        view: Toolbar? = null,
        title: String? = null,
        bBack: Boolean? = null,
        action: Boolean? = null,
        callBack: (() -> Unit)? = null
    ) {
        if (view == null) return

        if (!title.isNullOrEmpty()) view.title = title

        if (bBack != null && bBack) {
            view.setNavigationIcon(R.drawable.arrow_back)
            view.setNavigationOnClickListener { _ ->
                findNavController().popBackStack()
            }
        }

        if (action != null && action) {
            view.inflateMenu(R.menu.menu)
            view.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_lang -> {
                        if (callBack != null) {
                            callBack()
                        }
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }
    }

    fun Disposable.addToContainer() = disposableContainer.add(this)

    override fun onDestroyView() {
        disposableContainer.clear()
        super.onDestroyView()
    }
}
