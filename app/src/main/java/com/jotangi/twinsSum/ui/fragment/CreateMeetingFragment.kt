package com.jotangi.twinsSum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.jotangi.twinsSum.R
import com.jotangi.twinsSum.api.ApiUrl
import com.jotangi.twinsSum.api.main.RequestAddMeeting
import com.jotangi.twinsSum.databinding.FragmentCreateMeetingBinding
import com.jotangi.twinsSum.databinding.ToolbarBinding
import com.jotangi.twinsSum.ui.BaseFragment
import com.jotangi.twinsSum.utils.checkBlank
import com.jotangi.twinsSum.utils.txt
import kotlinx.coroutines.launch


class CreateMeetingFragment : BaseFragment() {

    private lateinit var binding: FragmentCreateMeetingBinding
    override fun getToolBar(): ToolbarBinding = binding.toolbar

    private var sex: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateMeetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initHandler()
        initCallBack()
    }

    private fun initView() {

        setArrowToolbar(getString(R.string.create_meeting_room))
    }

    private fun initHandler() {

        binding.apply {

            rg.setOnCheckedChangeListener { group, i ->

                sex = when (i) {

                    R.id.rb_man -> "1"
                    R.id.rb_woman -> "0"
                    else -> ""
                }
            }

            tvBirthday.setOnClickListener {

                dateTimeUtil.selectDay(
                    tvBirthday.txt(),
                    requireActivity(),
                    dateTxt = { tvBirthday.text = it }
                )
            }

            btCreate.setOnClickListener {

                if (listOf(etName, etId).checkBlank() ||
                    sex.isNullOrBlank() ||
                    tvBirthday.txt().isBlank()
                ) {

                    showToast("請填妥相關資料。")
                } else {
                    addMeetingApi()
                }
            }
        }
    }

    private fun addMeetingApi() {

        showProgress()

        lifecycleScope.launch {

            val request = binding.run {

                RequestAddMeeting(
                    etName.txt(),
                    etId.txt(),
                    sex ?: "",
                    tvBirthday.txt()
                )
            }

            val response = apiCall.addMeeting(request)

            if (response.code == ApiUrl.success)
                backPage()
            else
                showToast(response.responseMessage.toString())

            closeProgress()
        }
    }

    private fun initCallBack() {


    }
}