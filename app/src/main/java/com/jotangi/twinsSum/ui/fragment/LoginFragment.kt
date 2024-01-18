package com.jotangi.twinsSum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jotangi.twinsSum.R
import com.jotangi.twinsSum.api.ApiUrl
import com.jotangi.twinsSum.api.main.RequestLogin
import com.jotangi.twinsSum.databinding.FragmentLoginBinding
import com.jotangi.twinsSum.databinding.ToolbarBinding
import com.jotangi.twinsSum.ui.BaseFragment
import com.jotangi.twinsSum.utils.checkBlank
import com.jotangi.twinsSum.utils.txt
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    override fun getToolBar(): ToolbarBinding = binding.toolbar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initHandler()
        initCallBack()
    }

    private fun initView() {

        setToolbar("登入")
    }

    private fun initHandler() {

        binding.apply {

            tvLogin.setOnClickListener {

                if (listOf<EditText>(etAccount, etPwd).checkBlank())
                    showToast("請輸入帳號、密碼。")
                else
                    loginApi(
                        RequestLogin(
                            etAccount.txt(),
                            etPwd.txt()
                        )
                    )
            }
        }
    }

    private fun loginApi(request: RequestLogin) {

        showProgress()

        lifecycleScope.launch {

            val response = apiCall.login(request)

            if (response.code == ApiUrl.success) {

                infoCall.setAccount(binding.etAccount.txt())
                infoCall.setPwd(binding.etPwd.txt())
                popFragment(R.id.memberInfoFragment)
            } else {
                showToast(response.responseMessage.toString())
            }

            closeProgress()
        }
    }

    private fun initCallBack() {


    }
}