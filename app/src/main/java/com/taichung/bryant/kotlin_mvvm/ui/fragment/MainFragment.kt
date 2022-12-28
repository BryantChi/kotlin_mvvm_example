package com.taichung.bryant.kotlin_mvvm.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taichung.bryant.kotlin_mvvm.R
import com.taichung.bryant.kotlin_mvvm.adapter.AttractionsAdapter
import com.taichung.bryant.kotlin_mvvm.config.Config.DATA
import com.taichung.bryant.kotlin_mvvm.config.Config.LANG
import com.taichung.bryant.kotlin_mvvm.config.Config.LIMIT_PRE_PAGE
import com.taichung.bryant.kotlin_mvvm.data.attractions.AttractionsData
import com.taichung.bryant.kotlin_mvvm.databinding.DialogListLangBinding
import com.taichung.bryant.kotlin_mvvm.databinding.FragmentMainBinding
import com.taichung.bryant.kotlin_mvvm.listeners.ItemClickListener
import com.taichung.bryant.kotlin_mvvm.ui.base.BaseFragment
import com.taichung.bryant.kotlin_mvvm.ui.viewmodel.MainViewModel
import com.taichung.bryant.kotlin_mvvm.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    override fun getViewModelClass() = MainViewModel::class.java

    override fun getViewBinding() = FragmentMainBinding.inflate(layoutInflater)

    private lateinit var mLayoutManager: LinearLayoutManager
    private var attractionsAdapter: AttractionsAdapter? = null
    private var lastVisibleItem: Int = 0
    private var lang: String = ""
    private lateinit var sharedPreferences: SharedPreferences
    private var langDialog: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null
    private var langList: List<String> = listOf(
        "正體中文",
        "簡體中文",
        "英文",
        "日文",
        "韓文",
        "西班牙文",
        "印尼文",
        "泰文"
    )

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()

        sharedPreferences = requireContext().getSharedPreferences(DATA, 0)
        lang = sharedPreferences.getString(LANG, "zh-tw").toString()

        binding.llProgressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAttractionsAll(lang, viewModel.page.value ?: 1)
    }

    private fun initView() {
        attractionsAdapter = AttractionsAdapter(
            requireContext(),
            object : ItemClickListener {
                override fun itemClick(data: AttractionsData) {
                    val bundle = bundleOf(DATA to data)
                    findNavController().navigate(R.id.AttractionsDetailsFragment, bundle)
                }
            }
        )

        mLayoutManager = LinearLayoutManager(requireContext())
        binding.rvAttractions.apply {
            layoutManager = mLayoutManager
            adapter = attractionsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val isScrollToBottom =
                        newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == attractionsAdapter?.itemCount
                    val isLoadMore = viewModel.attractionsList.value?.size!! == LIMIT_PRE_PAGE

                    if (isScrollToBottom) {
                        if (isLoadMore) {
                            viewModel.getNextPage()
                            viewModel.getAttractionsAll(lang, viewModel.page.value ?: -1)
                        } else {
                            showToast(requireContext(), getString(R.string.no_more_info))
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
                }
            })
        }

        binding.toolbar.title = getString(R.string.app_name)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_lang -> {
                    showLangDialog()
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun initObserver() {
        viewModel.attractionsList.observe(viewLifecycleOwner) {
            if (viewModel.page.value != 1) {
                attractionsAdapter?.updateList(it)
                binding.rvAttractions.smoothScrollToPosition(
                    attractionsAdapter?.itemCount?.minus(30) ?: 0
                )
            } else {
                attractionsAdapter?.submitList(it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.llProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        attractionsAdapter = null
        viewModel.clearData()
        super.onDestroyView()
    }

    private fun showLangDialog() {
        langDialog = AlertDialog.Builder(requireContext())
        val dialogView = DialogListLangBinding.inflate(layoutInflater)
        val langAdapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, langList)
        val langListMap = listOf(
            "zh-tw",
            "zh-cn",
            "en",
            "ja",
            "ko",
            "es",
            "id",
            "th"
        )
        dialogView.lvLang.adapter = langAdapter
        dialogView.lvLang.setOnItemClickListener { parent, view, position, id ->
            sharedPreferences.edit()
                .putString(LANG, langListMap[position])
                .apply()
            viewModel.clearData()
            viewModel.getAttractionsAll(langListMap[position], viewModel.page.value ?: 1)
            dialog?.dismiss()
        }
        langAdapter.notifyDataSetChanged()
        dialog = langDialog?.setView(dialogView.root)?.create()
        dialog?.show()
    }
}
