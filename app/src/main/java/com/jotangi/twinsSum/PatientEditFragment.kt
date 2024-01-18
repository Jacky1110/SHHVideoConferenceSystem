package com.jotangi.twinsSum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.jotangi.twinsSum.api.ApiUrl
import com.jotangi.twinsSum.api.main.QueryPatientInfoBean
import com.jotangi.twinsSum.databinding.FragmentPatientEditBinding
import com.jotangi.twinsSum.databinding.ToolbarBinding
import com.jotangi.twinsSum.ui.BaseFragment
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File


class PatientEditFragment : BaseFragment() {

    private lateinit var binding: FragmentPatientEditBinding
    override fun getToolBar(): ToolbarBinding = binding.toolbar

    private val pictureLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->

        Timber.w("Uri: ${uri}")
        uri ?: return@registerForActivityResult

        val path = appUtil.getRealPathFromUri(requireContext(), uri)
        Timber.w("Path: ${path}")

        checkFile(path)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initHandler()
        initCallBack()
    }

    private fun initView() {

        setArrowToolbar("轉院病患編輯")
    }

    private fun initHandler() {

        binding.apply {

            tvSave.setOnClickListener {
                queryPatientInfoApi()
            }

            tvPicture.setOnClickListener {

                if (hasPermissions(*picturePermissions))
                    pictureLauncher.launch("image/*")
                else
                    picturePermissionsLaunch.launch(picturePermissions)
            }

        }
    }

    private fun checkFile(path: String?) {

        if (path.isNullOrBlank()) {
            showToast("路徑錯誤")
            return
        }

        val file = File(path)
        if (file.exists()) {

            Timber.w(": ${file.name} 存在")
            uploadPictureApi(file)
        } else {
            showToast("圖片不存在")
        }
    }

    private fun uploadPictureApi(file: File) {

        showProgress()

        lifecycleScope.launch {

            val response = apiCall.uploadPicture(
                apiViewModel.ldQueryPatientInfo.value?.get(0)?.bookingNo.toString(),
                file
            )

            if (response.code == ApiUrl.success) {
                showToast("上傳成功")
            } else {
                showToast(response.responseMessage.toString())
            }

            closeProgress()
        }
    }

    private fun queryPatientInfoApi() {

        showProgress()

        lifecycleScope.launch {

            with(binding) {

                apiViewModel.renewPatientInfo(
                    listOf(
                        apiViewModel.ldQueryPatientInfo.value?.get(0)?.bookingNo.toString(),
                        etB.text.toString(),
                        etC.text.toString(),
                        etD.text.toString(),
                        etE.text.toString(),
                        etF.text.toString(),
                        etG.text.toString(),
                        etH.text.toString(),
                        etI.text.toString(),
                        etJ.text.toString(),
                        etK.text.toString(),
                        etL.text.toString(),
                    ),
                    success = { backPage() },
                    fail = { showToast(it) }
                )
            }
        }
    }

    private fun initCallBack() {

        grantedPms = { pictureLauncher.launch("image/*") }

        deniedPms = { showToast("請同意儲存空間相關權限。") }

        apiViewModel.ldQueryPatientInfo.observe(viewLifecycleOwner) {
            ldInfo(it[0])
        }
    }

    private fun ldInfo(bean: QueryPatientInfoBean) {

        binding.apply {

            with(bean) {

                tvA.text = "A.病人姓名、性別、年齡\n病患姓名：${
                    patientName
                }\n身分證字號：${
                    patientPid
                }\n性別：${
                    if (patientGender == "0") "女" else "男"
                }\n年齡：${
                    dateTimeUtil.howOldMuch(patientBirthday)
                }"
                tvB.text = "B.此次中風前ADL(MRS)"
                tvC.text = "C.PHx/Drug history"
                tvD.text = "D.病人最後正常時間(On set time)"
                tvE.text = "E.病人到達他院急診時間"
                tvF.text = "F.主要的NE deficit、GCS、患側肌力"
                tvG.text = "G.初評NIHSS"
                tvH.text = "H.懷疑中風的territory/large vessel"
                tvI.text = "I.有施打IV-tPA、r-tPA劑量及施打時間"
                tvJ.text = "J.有施打IV-tPA後NIHSS分數"
                tvK.text = "K.無施打IV-tPA的原因"
                tvL.text = "L.無施打IV-tPA，是否有先投予antiplatelet及劑量"

                etB.setText(itemB.toString())
                etC.setText(itemC.toString())
                etD.setText(itemD.toString())
                etE.setText(itemE.toString())
                etF.setText(itemF.toString())
                etG.setText(itemG.toString())
                etH.setText(itemH.toString())
                etI.setText(itemI.toString())
                etJ.setText(itemJ.toString())
                etK.setText(itemK.toString())
                etL.setText(itemL.toString())
            }
        }
    }
}