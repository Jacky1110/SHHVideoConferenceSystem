package com.jotangi.twinsSum.api.main

import com.jotangi.twinsSum.api.ApiUrl
import com.jotangi.twinsSum.db.InfoCall
import com.jotangi.twinsSum.utils.title
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.core.component.KoinComponent
import timber.log.Timber
import java.io.File

class ApiCall(
    private val api: MainApi,
    private val infoCall: InfoCall
) : KoinComponent {

    /**
     * (1)	App會員登入
     */
    suspend fun login(
        request: RequestLogin
    ) = kotlin.runCatching {

        Timber.v("(1)\tApp會員登入".title())

        api.login(request.id, request.pwd, request.token, request.adId)
    }.getOrDefault(
        OpenData().apply { responseMessage = ApiUrl.connectFail }
    )

    /**
     * (2)	App會員登出
     */
    suspend fun logout() = kotlin.runCatching {

        Timber.v("(2)\tApp會員登出".title())

        api.logout(
            infoCall.getAccount().toString(),
            infoCall.getPwd().toString()
        )
    }.getOrDefault(
        OpenData().apply { responseMessage = ApiUrl.connectFail }
    )

    /**
     * (4)	新增會議室
     */
    suspend fun addMeeting(
        request: RequestAddMeeting
    ) = kotlin.runCatching {

        Timber.v("(4)\t新增會議室".title())

        api.addMeeting(
            infoCall.getAccount().toString(),
            infoCall.getPwd().toString(),
            request.name,
            request.pid,
            request.sex,
            request.birthday
        )
    }.getOrDefault(
        OpenData().apply { responseMessage = ApiUrl.connectFail }
    )

    /**
     * (5)	查詢會議室紀錄
     */
    suspend fun meetingList() = kotlin.runCatching {

        Timber.v("(5)\t查詢會議室紀錄".title())

        api.meetingList(
            infoCall.getAccount().toString(),
            infoCall.getPwd().toString(),
        )
    }.getOrDefault(
        MeetingListData().apply { responseMessage = ApiUrl.connectFail }
    )

    /**
     * (6)	查詢病患資料
     */
    suspend fun queryPatientInfo(bookingNo: String) = kotlin.runCatching {

        Timber.v("(5)\t查詢病患資料".title())

        api.queryPatientInfo(
            infoCall.getAccount().toString(),
            infoCall.getPwd().toString(),
            bookingNo
        )
    }.getOrDefault(
        QueryPatientInfoData().apply { responseMessage = ApiUrl.connectFail }
    )

    /**
     * (7)	更新病患資料
     */
    suspend fun renewPatientInfo(list: List<String>) = kotlin.runCatching {

        Timber.v("(7)\t更新病患資料".title())

        api.renewPatientInfo(
            infoCall.getAccount().toString(),
            infoCall.getPwd().toString(),
            list[0],
            list[1],
            list[2],
            list[3],
            list[4],
            list[5],
            list[6],
            list[7],
            list[8],
            list[9],
            list[10],
            list[11],
        )
    }.getOrDefault(
        OpenData().apply { responseMessage = ApiUrl.connectFail }
    )

    /**
     * (8)	會員上傳資訊檔案
     */
    suspend fun uploadPicture(bookingNo: String, file: File) = kotlin.runCatching {

        Timber.v("(8)\t會員上傳資訊檔案".title())

        val multipartBody = MultipartBody.Builder().run {
            setType(MultipartBody.FORM)
            addFormDataPart("member_id", infoCall.getAccount().toString())
            addFormDataPart("member_pwd", infoCall.getPwd().toString())
            addFormDataPart("booking_no", bookingNo)
            addFormDataPart(
                name = "upload_filename",
                filename = file.name,
                body = file.asRequestBody("image/*".toMediaType())
            )
        }

        api.uploadPicture(multipartBody.build())
    }.getOrDefault(
        OpenData().apply { responseMessage = ApiUrl.connectFail }
    )

    /**
     * (9)	資訊檔案列表
     */
    suspend fun pictureList(bookingNo: String) = kotlin.runCatching {

        Timber.v("(9)\t資訊檔案列表".title())

        api.pictureList(
            infoCall.getAccount().toString(),
            infoCall.getPwd().toString(),
            bookingNo
        )
    }.getOrDefault(
        PictureListData().apply { responseMessage = ApiUrl.connectFail }
    )

    /**
     * (10)	關閉會議室
     */
    suspend fun closeMeeting(bookingNo: String) = kotlin.runCatching {

        Timber.v("(10)\t關閉會議室".title())

        api.closeMeeting(
            infoCall.getAccount().toString(),
            infoCall.getPwd().toString(),
            bookingNo
        )
    }.getOrDefault(
        OpenData().apply { responseMessage = ApiUrl.connectFail }
    )
}