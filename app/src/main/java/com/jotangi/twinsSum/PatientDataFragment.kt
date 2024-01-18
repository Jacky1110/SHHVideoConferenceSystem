package com.jotangi.twinsSum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.jotangi.twinsSum.api.ApiUrl
import com.jotangi.twinsSum.api.main.PictureListBean
import com.jotangi.twinsSum.api.main.QueryPatientInfoBean
import com.jotangi.twinsSum.databinding.FragmentPatientDataBinding
import com.jotangi.twinsSum.databinding.ToolbarBinding
import com.jotangi.twinsSum.ui.BaseFragment
import com.jotangi.twinsSum.utils.Const
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import kotlinx.coroutines.launch
import timber.log.Timber

class PatientDataFragment : BaseFragment() {

    private lateinit var binding: FragmentPatientDataBinding
    override fun getToolBar(): ToolbarBinding = binding.toolbar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initApi()
        initView()
        initHandler()
        initCallBack()
    }

    private fun initApi() {

        lifecycleScope.launch {

            apiViewModel.pictureList(
                apiViewModel.ldQueryPatientInfo.value?.get(0)?.bookingNo.toString(),
                success = {}
            )
        }
    }

    private fun initView() {

        setArrowToolbar("轉院病患資料")
    }

    private fun initHandler() {

        binding.tvEdit.setOnClickListener {
            findNavController().navigate(R.id.patientEditFragment)
        }
    }

    private fun initCallBack() {

        apiViewModel.apply {

            ldQueryPatientInfo.observe(viewLifecycleOwner) { ldInfo(it[0]) }

            ldPictureList.observe(viewLifecycleOwner) { bannerView(it) }
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
                tvB.text = "B.此次中風前ADL(MRS)\n${itemB}"
                tvC.text = "C.PHx/Drug history\n${itemC}"
                tvD.text = "D.病人最後正常時間(On set time)\n${itemD}"
                tvE.text = "E.病人到達他院急診時間\n${itemE}"
                tvF.text = "F.主要的NE deficit、GCS、患側肌力\n${itemF}"
                tvG.text = "G.初評NIHSS\n${itemG}"
                tvH.text = "H.懷疑中風的territory/large vessel\n${itemH}"
                tvI.text = "I.有施打IV-tPA、r-tPA劑量及施打時間\n${itemI}"
                tvJ.text = "J.有施打IV-tPA後NIHSS分數\n${itemJ}"
                tvK.text = "K.無施打IV-tPA的原因\n${itemK}"
                tvL.text = "L.無施打IV-tPA，是否有先投予antiplatelet及劑量\n${itemL}"
            }
        }
    }

    private fun bannerView(list: List<PictureListBean>) {

        binding.banner.setAdapter(object : BannerImageAdapter<PictureListBean>(list) {
            override fun onBindView(
                holder: BannerImageHolder?,
                data: PictureListBean?,
                position: Int,
                size: Int
            ) {

                if (holder == null) return

                Timber.w("url: ${ApiUrl.picture + data?.opPic}")

                Glide.with(holder.itemView)
                    .load(ApiUrl.picture + data?.opPic)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                    .into(holder.imageView)

                holder.itemView.setOnClickListener {

                    if (data?.opPic.isNullOrBlank()) {

                        showToast("參數錯誤：${data?.opPic}")
                    } else {

                        findNavController().navigate(
                            R.id.patientPictureFragment,
                            bundleOf(Const.pictureName to data?.opPic)
                        )
                    }
                }
            }
        }).addBannerLifecycleObserver(viewLifecycleOwner)
            .setBannerGalleryEffect(30, 40, 1F)
            .setIndicator(binding.bannerI, false)
    }
}