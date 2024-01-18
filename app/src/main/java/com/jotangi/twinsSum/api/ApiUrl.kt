package com.jotangi.twinsSum.api


class ApiUrl {

    companion object {

        /**
         * server
         */
        private const val server = "https://clinic.healthme.com.tw/"

        /**
         * 組件
         */
        private const val api = "medicalec/api/"
        private const val shh = "shh/"

        /**
         * 種類
         */
        const val main = "${server}${api}"
        const val picture = "${server}${shh}"

        /**
         * API
         */
        // (1)	App會員登入
        const val login = "acc_login.php"

        // (2)	App會員登出
        const val logout = "acc_logout.php"

        // (4)	新增會議室
        const val addMeeting = "add_meeting.php"

        // (5)	查詢會議室紀錄
        const val meetingList = "meeting_list.php"

        // (6)	查詢病患資料
        const val queryPatientInfo = "outpatientinfo.php"

        // (7)	更新病患資料
        const val renewPatientInfo = "upd_outpatientinfo.php"

        // (8)	會員上傳資訊檔案
        const val uploadPicture = "upload_opfile.php"

        // (9)	資訊檔案列表
        const val pictureList = "opfile_list.php"

        // (10)	關閉會議室
        const val closeMeeting = "close_meeting.php"

        /**
         * 結果
         */
        const val success = "0x0200"
        const val connectFail = "連線失敗。"
        const val wait = "尚未開放，敬請期待。"
    }
}