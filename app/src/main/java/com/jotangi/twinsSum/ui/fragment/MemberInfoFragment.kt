package com.jotangi.twinsSum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.jotangi.twinsSum.BuildConfig
import com.jotangi.twinsSum.R
import com.jotangi.twinsSum.api.ApiUrl
import com.jotangi.twinsSum.databinding.FragmentMemberInfoBinding
import com.jotangi.twinsSum.databinding.ToolbarBinding
import com.jotangi.twinsSum.ui.BaseFragment
import kotlinx.coroutines.launch


class MemberInfoFragment : BaseFragment() {

    private lateinit var binding: FragmentMemberInfoBinding
    override fun getToolBar(): ToolbarBinding = binding.toolbar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemberInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initHandler()
        initCallBack()
    }

    private fun initView() {

        setToolbar("會員中心")

        binding.apply {

            tvAccount.text = "帳號：${infoCall.getAccount().toString()}"
            tvVersion.text = "版號：${BuildConfig.VERSION_NAME}"
        }
    }

    private fun initHandler() {

        binding.btLogout.setOnClickListener { logoutApi() }
    }

    private fun logoutApi() {

        showProgress()

        lifecycleScope.launch {

            val response = apiCall.logout()

            if (response.code == ApiUrl.success) {

                infoCall.emptyAll()
                popFragment(R.id.loginFragment)
            } else {

                showToast(response.responseMessage.toString())
            }

            closeProgress()
        }
    }

    private fun initCallBack() {


    }
}