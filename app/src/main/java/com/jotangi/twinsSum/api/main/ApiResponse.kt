package com.jotangi.twinsSum.api.main

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * 共用
 */
@Parcelize
open class OpenData(
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("code")
    var code: String? = null,
    @SerializedName("responseMessage")
    var responseMessage: String? = null
) : Parcelable


/**
 * (5)	查詢會議室紀錄
 */
@Parcelize
data class MeetingListData(
    @SerializedName("list")
    var list: List<MeetingListBean> = listOf(),
) : Parcelable, OpenData()

@Parcelize
data class MeetingListBean(
    @SerializedName("booking_no")
    var bookingNo: String? = null,
    @SerializedName("meetingUrl")
    var meetingUrl: String? = null,
    @SerializedName("meetingUrl2")
    var meetingUrl2: String? = null,
    @SerializedName("member_id")
    var memberId: String? = null,
    @SerializedName("member_name")
    var memberName: String? = null,
    @SerializedName("member_type")
    var memberType: String? = null,
    @SerializedName("mid")
    var mid: String? = null,
    @SerializedName("outpatient_date")
    var outpatientDate: String? = null,
    @SerializedName("outpatientlist_created_at")
    var outpatientlistCreatedAt: String? = null,
    @SerializedName("patient_birthday")
    var patientBirthday: String? = null,
    @SerializedName("patient_gender")
    var patientGender: String? = null,
    @SerializedName("patient_name")
    var patientName: String? = null,
    @SerializedName("patient_pid")
    var patientPid: String? = null,
    @SerializedName("reserve_status")
    var reserveStatus: String? = null,
    @SerializedName("rid")
    var rid: String? = null,
) : Parcelable

/**
 * (6)	查詢病患資料
 */
@Parcelize
data class QueryPatientInfoData(
    @SerializedName("list")
    var list: List<QueryPatientInfoBean> = listOf(),
) : Parcelable, OpenData()

@Parcelize
data class QueryPatientInfoBean(
    @SerializedName("access_code")
    var accessCode: String? = null,
    @SerializedName("booking_no")
    var bookingNo: String? = null,
    @SerializedName("itemB")
    var itemB: String? = null,
    @SerializedName("itemC")
    var itemC: String? = null,
    @SerializedName("itemD")
    var itemD: String? = null,
    @SerializedName("itemE")
    var itemE: String? = null,
    @SerializedName("itemF")
    var itemF: String? = null,
    @SerializedName("itemG")
    var itemG: String? = null,
    @SerializedName("itemH")
    var itemH: String? = null,
    @SerializedName("itemI")
    var itemI: String? = null,
    @SerializedName("itemJ")
    var itemJ: String? = null,
    @SerializedName("itemK")
    var itemK: String? = null,
    @SerializedName("itemL")
    var itemL: String? = null,
    @SerializedName("meetingUrl")
    var meetingUrl: String? = null,
    @SerializedName("meetingUrl2")
    var meetingUrl2: String? = null,
    @SerializedName("meetingid")
    var meetingid: String? = null,
    @SerializedName("member_id")
    var memberId: String? = null,
    @SerializedName("member_name")
    var memberName: String? = null,
    @SerializedName("member_type")
    var memberType: String? = null,
    @SerializedName("memo")
    var memo: String? = null,
    @SerializedName("mid")
    var mid: String? = null,
    @SerializedName("outpatient_date")
    var outpatientDate: String? = null,
    @SerializedName("outpatient_flag")
    var outpatientFlag: String? = null,
    @SerializedName("outpatientlist_created_at")
    var outpatientlistCreatedAt: String? = null,
    @SerializedName("outpatientlist_created_by")
    var outpatientlistCreatedBy: String? = null,
    @SerializedName("outpatientlist_trash")
    var outpatientlistTrash: String? = null,
    @SerializedName("patient_birthday")
    var patientBirthday: String? = null,
    @SerializedName("patient_gender")
    var patientGender: String? = null,
    @SerializedName("patient_name")
    var patientName: String? = null,
    @SerializedName("patient_phone")
    var patientPhone: String? = null,
    @SerializedName("patient_pid")
    var patientPid: String? = null,
    @SerializedName("pincode")
    var pincode: String? = null,
    @SerializedName("reserve_status")
    var reserveStatus: String? = null,
    @SerializedName("rid")
    var rid: String? = null,
    @SerializedName("vmr")
    var vmr: String? = null,
    @SerializedName("vmrid")
    var vmrid: String? = null,
) : Parcelable


/**
 * (9)	資訊檔案列表
 */
@Parcelize
data class PictureListData(
    @SerializedName("list")
    var list: List<PictureListBean> = listOf(),
) : Parcelable, OpenData()

@Parcelize
data class PictureListBean(
    @SerializedName("booking_no")
    var bookingNo: String? = null,
    @SerializedName("member_id")
    var memberId: String? = null,
    @SerializedName("oid")
    var oid: String? = null,
    @SerializedName("op_pic")
    var opPic: String? = null,
    @SerializedName("upload_date")
    var uploadDate: String? = null,
) : Parcelable