package com.jotangi.twinsSum.api.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * (1)	App會員登入
 */
@Parcelize
open class RequestLogin(
    var id: String,
    var pwd: String,
    var token: String? = null,
    var adId: String? = null
) : Parcelable

/**
 * (4)	新增會議室
 */
@Parcelize
open class RequestAddMeeting(
    var name: String,
    var pid: String,
    var sex: String,
    var birthday: String
) : Parcelable