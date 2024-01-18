package com.jotangi.twinsSum.api.main

import com.jotangi.twinsSum.api.ApiUrl
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface MainApi {

    /**
     * (1)	App會員登入
     */
    @FormUrlEncoded
    @POST(ApiUrl.login)
    suspend fun login(
        @Field("member_id") id: String,
        @Field("member_pwd") pwd: String,
        @Field("FCM_Token") token: String?,
        @Field("unique_id") adId: String?,
    ): OpenData

    /**
     * (2)	App會員登出
     */
    @FormUrlEncoded
    @POST(ApiUrl.logout)
    suspend fun logout(
        @Field("member_id") id: String,
        @Field("member_pwd") pwd: String,
    ): OpenData

    /**
     * (4)	新增會議室
     */
    @FormUrlEncoded
    @POST(ApiUrl.addMeeting)
    suspend fun addMeeting(
        @Field("member_id") id: String,
        @Field("member_pwd") pwd: String,
        @Field("patient_name") name: String,
        @Field("patient_pid") pid: String,
        @Field("patient_gender") sex: String,
        @Field("patient_birthday") birthday: String,
    ): OpenData

    /**
     * (5)	查詢會議室紀錄
     */
    @FormUrlEncoded
    @POST(ApiUrl.meetingList)
    suspend fun meetingList(
        @Field("member_id") id: String,
        @Field("member_pwd") pwd: String,
    ): MeetingListData

    /**
     * (6)	查詢病患資料
     */
    @FormUrlEncoded
    @POST(ApiUrl.queryPatientInfo)
    suspend fun queryPatientInfo(
        @Field("member_id") id: String,
        @Field("member_pwd") pwd: String,
        @Field("booking_no") bookingNo: String,
    ): QueryPatientInfoData

    /**
     * (7)	更新病患資料
     */
    @FormUrlEncoded
    @POST(ApiUrl.renewPatientInfo)
    suspend fun renewPatientInfo(
        @Field("member_id") id: String,
        @Field("member_pwd") pwd: String,
        @Field("booking_no") bookingNo: String,
        @Field("itemB") b: String,
        @Field("itemC") c: String,
        @Field("itemD") d: String,
        @Field("itemE") e: String,
        @Field("itemF") f: String,
        @Field("itemG") g: String,
        @Field("itemH") h: String,
        @Field("itemI") i: String,
        @Field("itemJ") j: String,
        @Field("itemK") k: String,
        @Field("itemL") l: String,
    ): OpenData

    /**
     * (8)	會員上傳資訊檔案
     */
    @POST(ApiUrl.uploadPicture)
    suspend fun uploadPicture(
        @Body body: RequestBody,
    ): OpenData


    /**
     * (9)	資訊檔案列表
     */
    @FormUrlEncoded
    @POST(ApiUrl.pictureList)
    suspend fun pictureList(
        @Field("member_id") id: String,
        @Field("member_pwd") pwd: String,
        @Field("booking_no") bookingNo: String,
    ): PictureListData

    /**
     * (10)	關閉會議室
     */
    @FormUrlEncoded
    @POST(ApiUrl.closeMeeting)
    suspend fun closeMeeting(
        @Field("member_id") id: String,
        @Field("member_pwd") pwd: String,
        @Field("booking_no") bookingNo: String,
    ): OpenData
}