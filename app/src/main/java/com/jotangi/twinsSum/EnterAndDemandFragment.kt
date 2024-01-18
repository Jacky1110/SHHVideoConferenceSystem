package com.jotangi.twinsSum

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jotangi.twinsSum.api.main.MeetingListBean
import com.jotangi.twinsSum.databinding.FragmentEnterAndDemandBinding
import com.jotangi.twinsSum.databinding.ToolbarBinding
import com.jotangi.twinsSum.ui.BaseFragment
import com.jotangi.twinsSum.ui.adapter.MeetingAdapter
import kotlinx.coroutines.launch

class EnterAndDemandFragment : BaseFragment() {

    private lateinit var binding: FragmentEnterAndDemandBinding
    override fun getToolBar(): ToolbarBinding = binding.toolbar

    private lateinit var meetingAdapter: MeetingAdapter
    private var list = ArrayList<MeetingListBean>()
    private var isOld = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterAndDemandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initHandler()
        initCallBack()
    }

    private fun initView() {

        setArrowToolbar("轉院病患資料輸入/查詢")

        meetingAdapter = MeetingAdapter(list, dateTimeUtil, true)

        binding.rv.apply {

            layoutManager = LinearLayoutManager(requireContext())

            adapter = meetingAdapter
        }
    }

    private fun initHandler() {

        binding.apply {

            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {

                    ivClose.visibility = if (p0.toString().isEmpty())
                        View.INVISIBLE else View.VISIBLE

                    searchNames(p0.toString())
                }
            })

            ivClose.setOnClickListener {

                ivClose.visibility = View.INVISIBLE
                etSearch.text.clear()
            }

            tvNewOld.setOnClickListener {

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
        }

        meetingAdapter.enterClick = {

            if (it.bookingNo.isNullOrBlank())
                showToast("BookingNo 錯誤: ${it.bookingNo}")
            else
                queryPatientInfoApi(it.bookingNo!!)
        }
    }

    private fun queryPatientInfoApi(bookingNo: String) {

        showProgress()

        lifecycleScope.launch {

            apiViewModel.queryPatientInfo(
                bookingNo,
                success = {
                    findNavController().navigate(R.id.patientDataFragment)
                },
                fail = { showToast(it) }
            )

            closeProgress()
        }
    }

    private fun initCallBack() {

        apiViewModel.ldMeetingList.observe(viewLifecycleOwner) {

            list.clear()
            list.addAll(it)
            meetingAdapter.notifyDataSetChanged()
        }
    }

    private fun searchNames(name: String) {

        if (name.isBlank()) {

            apiViewModel.ldMeetingList.observe(viewLifecycleOwner) {

                list.clear()
                list.addAll(it)
                meetingAdapter.notifyDataSetChanged()
            }
        }

        val dataMutable = mutableListOf<MeetingListBean>()

        apiViewModel.ldMeetingList.value?.forEach {

            it.patientPid?.also { pid ->

                if (pid.contains(name)) dataMutable.add(it)
            }
        }

        list.clear()
        list.addAll(dataMutable)
        meetingAdapter.notifyDataSetChanged()
    }
}