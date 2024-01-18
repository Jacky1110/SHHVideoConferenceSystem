package com.jotangi.twinsSum


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jotangi.twinsSum.api.ApiUrl
import com.jotangi.twinsSum.databinding.FragmentPatientPictureBinding
import com.jotangi.twinsSum.databinding.ToolbarBinding
import com.jotangi.twinsSum.ui.BaseFragment
import com.jotangi.twinsSum.utils.Const

class PatientPictureFragment : BaseFragment() {

    private lateinit var binding: FragmentPatientPictureBinding
    override fun getToolBar(): ToolbarBinding = binding.toolbar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initHandler()
        initCallBack()
    }

    private fun initView() {

        setArrowToolbar("轉院病患圖片")

        val name = arguments?.getString(Const.pictureName)

        if (name.isNullOrBlank()) showToast("檔名錯誤：$name")

        binding.wv.apply {

            with(settings) {

                builtInZoomControls = true
                displayZoomControls = false

                val imageUrl = ApiUrl.picture + name
                val html = "<html><body><img src=\"$imageUrl\" width=\"100%\"/></body></html>"
                loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
            }
        }
    }

    private fun initHandler() {


    }

    private fun initCallBack() {


    }
}