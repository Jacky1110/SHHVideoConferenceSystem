package com.jotangi.twinsSum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jotangi.twinsSum.R
import com.jotangi.twinsSum.databinding.FragmentHomeBinding
import com.jotangi.twinsSum.databinding.ToolbarBinding
import com.jotangi.twinsSum.ui.BaseFragment
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun getToolBar(): ToolbarBinding = binding.toolbar


    // 測試登入帳號
    // shh-1/10640785
    // shh-2/10640785
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initHandler()
        initCallBack()
    }

    private fun initView() {

        setToolbar("主頁")
    }

    private fun initHandler() {

        binding.apply {

            tvCreate.setOnClickListener {
                findNavController().navigate(R.id.createMeetingFragment)
            }

            tvEnter.setOnClickListener { meetingListApi(1) }

            // 轉院病患資料輸入/查詢
            tvDemand.setOnClickListener { meetingListApi(2) }

            tvRecord.setOnClickListener { }
        }
    }

    private fun meetingListApi(n: Int) {

        showProgress()

        lifecycleScope.launch {

            apiViewModel.meetingList(
                success = {

                    if (n == 1)
                        findNavController().navigate(R.id.enterMeetingFragment)
                    else
                        findNavController().navigate(R.id.enterAndDemandFragment)
                }
            ) { showToast(it) }

            closeProgress()
        }
    }

    private fun initCallBack() {


    }
}