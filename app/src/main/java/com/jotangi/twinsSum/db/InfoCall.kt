package com.jotangi.twinsSum.db

import com.jotangi.twinsSum.utils.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import timber.log.Timber


class InfoCall(private val dao: InfoDao) : KoinComponent {

    fun setAccount(value: String?) {
        setCoroutine(1, Const.i01_account, value)
    }

    fun getAccount(): String? = getBlocking(Const.i01_account)

    fun setPwd(value: String?) {
        setCoroutine(2, Const.i02_password, value)
    }

    fun getPwd(): String? = getBlocking(Const.i02_password)


    /**
     * other
     */
    fun emptyAll() {

        CoroutineScope(Dispatchers.IO).launch {

            kotlin.runCatching {
                dao.emptyAll()
            }.onSuccess {
                Timber.w("${Const.infoTableName} 清空資料 成功")
            }.onFailure {
                Timber.e("${Const.infoTableName} 清空資料 失敗")
            }
        }
    }


    /**
     * Set
     */
    private fun setCoroutine(id: Int? = null, key: String, value: String?) {

        CoroutineScope(Dispatchers.IO).launch {

            runCatching {
                dao.upsert(InfoTable(id, key, value))
            }.onSuccess {
                Timber.v("儲存成功: ${InfoTable(id, key, value)}")
            }.onFailure {
                Timber.e("儲存失敗: ${InfoTable(id, key, value)}")
            }
        }
    }

    /**
     * Get runBlocking
     */
    private fun getBlocking(value: String): String? =
        runBlocking(Dispatchers.IO) {

            runCatching {
                dao.keyByValue(value)
            }.onSuccess {
                Timber.v("取得資料: ($it)")
            }.onFailure {
                it.printStackTrace()
            }.getOrNull()
        }
}