package com.jotangi.twinsSum.utils


class Const {

    companion object {

        /**
         * 攔截器
         */
        val loggingRegex = Regex(
            "(--> (POST|GET)|<-- (END HTTP|200 OK|END POST))"
        )
        const val loggingFail = "<-- HTTP FAILED"

        /**
         * DB
         */
        const val infoFileName = "Info.db"
        const val infoTableName = "info"

        // type
        const val infoKey = "infoKey"
        const val infoValue = "infoValue"
        const val infoTime = "infoTime"

        // key
        const val i01_account = "帳號"
        const val i02_password = "密碼"

        /**
         * bundle
         */
        const val webUrl = "webUrl"
        const val pictureName = "pictureName"
    }
}