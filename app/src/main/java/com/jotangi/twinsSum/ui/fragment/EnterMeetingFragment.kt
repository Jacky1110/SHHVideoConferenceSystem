package com.jotangi.twinsSum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jotangi.twinsSum.R
import com.jotangi.twinsSum.api.main.MeetingListBean
import com.jotangi.twinsSum.databinding.FragmentEnterMeetingBinding
import com.jotangi.twinsSum.databinding.ToolbarBinding
import com.jotangi.twinsSum.ui.BaseFragment
import com.jotangi.twinsSum.ui.adapter.MeetingAdapter
import com.jotangi.twinsSum.utils.Const
import kotlinx.coroutines.launch
import timber.log.Timber

class EnterMeetingFragment : BaseFragment() {

    private lateinit var binding: FragmentEnterMeetingBinding
    override fun getToolBar(): ToolbarBinding = binding.toolbar

    private lateinit var meetingAdapter: MeetingAdapter
    private var list = ArrayList<MeetingListBean>()
    private var url: String? = null
    private var isOld = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterMeetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initHandler()
        initCallBack()
    }

    private fun initView() {

        setArrowToolbar(getString(R.string.enter_meeting))

        meetingAdapter = MeetingAdapter(list, dateTimeUtil)

        binding.rv.apply {

            layoutManager = LinearLayoutManager(requireContext())

            adapter = meetingAdapter
        }
    }

    private fun initHandler() {

        binding.tvNewOld.setOnClickListener {

            var ldList = apiViewModel.ldMeetingList.value

            if (ldList.isNullOrEmpty()) return@setOnClickListener

            ldList = if (isOld) {

                isOld = false
                ldList.sortedByDescending { it.outpatientDate }
            } else {

                isOld = true
                ldList.sortedBy { it.outpatientDate }
            }

            list.clear()
            list.addAll(ldList)
            meetingAdapter.notifyDataSetChanged()
        }

        meetingAdapter.apply {

            enterClick = {

                Timber.w("click: $it")

                url = if (infoCall.getAccount() == "shh-1")
                    it.meetingUrl
                else
                    it.meetingUrl2

                when {

                    url.isNullOrBlank() -> showToast("url 錯誤。")

                    !hasPermissions(*videoPermissions) -> {
                        videoLaunch.launch(videoPermissions)
                    }

                    else -> findNavController().navigate(
                        R.id.meetingRoomFragment,
                        bundleOf(Const.webUrl to url)
                    )
                }
            }

            closeClick = { closeMeetingApi() }
        }
    }

    private fun closeMeetingApi() {

        showProgress()

        lifecycleScope.launch {

            apiViewModel.closeMeeting(
                apiViewModel.ldMeetingList.value?.get(0)?.bookingNo.toString(),
                fail = { showToast(it) }
            )

            closeProgress()
        }
    }

    private fun initCallBack() {

        grantedPms = {
            findNavController().navigate(
                R.id.meetingRoomFragment,
                bundleOf(Const.webUrl to url)
            )
        }

        deniedPms = { showToast("請同意視訊相關權限。") }

        apiViewModel.ldMeetingList.observe(viewLifecycleOwner) {

            list.clear()
            list.addAll(it)
            meetingAdapter.notifyDataSetChanged()
        }
    }
}