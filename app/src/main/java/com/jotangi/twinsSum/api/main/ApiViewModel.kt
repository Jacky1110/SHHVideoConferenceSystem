package com.jotangi.twinsSum.api.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.jotangi.twinsSum.api.ApiUrl

class ApiViewModel(private var apiCall: ApiCall) : ViewModel() {

    // (5)	查詢會議室紀錄
    var ldMeetingList = MediatorLiveData<List<MeetingListBean>>()

    // (6)	查詢病患資料
    var ldQueryPatientInfo = MediatorLiveData<List<QueryPatientInfoBean>>()

    // (9)	資訊檔案列表
    var ldPictureList = MediatorLiveData<List<PictureListBean>>()


    /**
     * (5)	查詢會議室紀錄
     */
    suspend fun meetingList(
        success: (() -> Unit)?,
        fail: (String) -> Unit
    ) {

        val response = apiCall.meetingList()

        if (response.code == ApiUrl.success &&
            response.list.isNotEmpty()
        ) {

            ldMeetingList.postValue(response.list)
            success?.invoke()
        } else {

            fail(response.responseMessage.toString())
        }
    }

    /**
     * (6)	查詢病患資料
     */
    suspend fun queryPatientInfo(
        bookingNo: String,
        success: () -> Unit,
        fail: (String) -> Unit
    ) {

        val response = apiCall.queryPatientInfo(bookingNo)

        if (response.code == ApiUrl.success &&
            response.list.isNotEmpty()
        ) {

            ldQueryPatientInfo.postValue(response.list)
            success()
        } else {

            fail(response.responseMessage.toString())
        }
    }

    /**
     * (7)	更新病患資料
     */
    suspend fun renewPatientInfo(
        list: List<String>,
        success: () -> Unit,
        fail: (String) -> Unit
    ) {

        val renewResponse = apiCall.renewPatientInfo(list)

        if (renewResponse.code == ApiUrl.success) {

            queryPatientInfo(list[0], success, fail)
        } else {

            fail(renewResponse.responseMessage.toString())
        }
    }

    /**
     * (9)	資訊檔案列表
     */
    suspend fun pictureList(
        bookingNo: String,
        success: () -> Unit,
    ) {

        val response = apiCall.pictureList(bookingNo)

        if (response.code == ApiUrl.success &&
            response.list.isNotEmpty()
        ) {

            ldPictureList.postValue(response.list)
            success()
        }
    }

    /**
     * (10)	關閉會議室
     */
    suspend fun closeMeeting(
        bookingNo: String,
        fail: (String) -> Unit
    ) {

        val response = apiCall.closeMeeting(bookingNo)

        if (response.code == ApiUrl.success) {

            meetingList(null, fail)
        } else {

            fail(response.responseMessage.toString())
        }
    }
}