package com.jotangi.twinsSum

import androidx.room.Room
import com.jotangi.twinsSum.api.ApiUrl
import com.jotangi.twinsSum.api.main.ApiCall
import com.jotangi.twinsSum.api.main.ApiViewModel
import com.jotangi.twinsSum.api.main.MainApi
import com.jotangi.twinsSum.db.InfoCall
import com.jotangi.twinsSum.db.InfoDataBase
import com.jotangi.twinsSum.utils.AppUtil
import com.jotangi.twinsSum.utils.Const
import com.jotangi.twinsSum.utils.DateTimeUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.net.URLDecoder
import java.util.concurrent.TimeUnit


object KoinModules {

    private val network = module {

        single {

            HttpLoggingInterceptor { msg ->

                kotlin.runCatching {

                    URLDecoder.decode(msg, Charsets.UTF_8.toString()).also {

                        when {

                            Const.loggingRegex.containsMatchIn(msg) ->
                                Timber.w("$it")

                            msg.contains(Const.loggingFail) -> Timber.e("$it")

                            else -> Timber.i("$it")
                        }
                    }
                }.onFailure {

                    Timber.w("$msg")
                }
            }.apply {

                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        single {

            OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
        }

        single {

            val retrofit = Retrofit.Builder()
                .baseUrl(ApiUrl.main)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()

            retrofit.create(MainApi::class.java)
        }

        singleOf(::ApiCall)

        single { ApiViewModel(get()) }
    }

    private val db = module {

        single {
            Room.databaseBuilder(
                androidApplication(),
                InfoDataBase::class.java,
                Const.infoFileName
            ).build()
        }

        single {
            get<InfoDataBase>().getInfoDao()
        }

        singleOf(::InfoCall)
    }

    private val utils = module {

        singleOf(::DateTimeUtil)
        singleOf(::AppUtil)
    }

    val list = listOf(network, db, utils)
}