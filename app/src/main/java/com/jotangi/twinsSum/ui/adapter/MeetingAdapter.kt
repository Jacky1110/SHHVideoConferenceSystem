package com.jotangi.twinsSum.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jotangi.twinsSum.api.main.MeetingListBean
import com.jotangi.twinsSum.databinding.AdapterMeetingBinding
import com.jotangi.twinsSum.utils.DateTimeUtil
import com.jotangi.twinsSum.utils.gone


class MeetingAdapter(
    private val list: List<MeetingListBean>,
    private val dateTimeUtil: DateTimeUtil,
    private val isGone: Boolean? = null
) : RecyclerView.Adapter<MeetingAdapter.ViewHolder>() {

    var enterClick: (MeetingListBean) -> Unit = {}
    var closeClick: (MeetingListBean) -> Unit = {}

    inner class ViewHolder(val binding: AdapterMeetingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MeetingListBean) {

            binding.apply {

                tvName.text = "病患名稱：${data.patientName.toString()}"
                tvId.text = "身分證字號：${data.patientPid.toString()}"
                tvSex.text = "性別：${if (data.patientGender == "0") "女" else "男"}"
                tvOld.text = "年齡：${dateTimeUtil.howOldMuch(data.patientBirthday)}歲"

                if (isGone == true) {
                    tvEnter.gone()
                    tvClose.gone()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterMeetingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(list[position])

        if (isGone == true) {

            holder.itemView.setOnClickListener {
                enterClick(list[position])
            }
        } else {

            holder.binding.tvEnter.setOnClickListener {
                enterClick(list[position])
            }

            holder.binding.tvClose.setOnClickListener {
                closeClick(list[position])
            }
        }
    }

    override fun getItemCount() = list.size
}