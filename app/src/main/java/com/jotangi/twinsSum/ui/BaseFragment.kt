package com.jotangi.twinsSum.ui

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jotangi.twinsSum.R
import com.jotangi.twinsSum.api.ApiUrl
import com.jotangi.twinsSum.api.main.ApiCall
import com.jotangi.twinsSum.api.main.ApiViewModel
import com.jotangi.twinsSum.databinding.ToolbarBinding
import com.jotangi.twinsSum.db.InfoCall
import com.jotangi.twinsSum.ui.dialog.LoadingDialog
import com.jotangi.twinsSum.utils.AppUtil
import com.jotangi.twinsSum.utils.DateTimeUtil
import com.jotangi.twinsSum.utils.gone
import com.jotangi.twinsSum.utils.title
import com.jotangi.twinsSum.utils.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


abstract class BaseFragment : Fragment() {

    private var mainActivity: MainActivity? = null
    abstract fun getToolBar(): ToolbarBinding?

    val apiViewModel: ApiViewModel by viewModel()
    val apiCall: ApiCall by inject()
    val infoCall: InfoCall by inject()
    val dateTimeUtil: DateTimeUtil by inject()
    val appUtil: AppUtil by inject()

    private var toast: Toast? = null
    private var loadingDialog: LoadingDialog? = null

    /**
     * 權限
     */
    var grantedPms: () -> Unit = {}
    var deniedPms: () -> Unit = {}

    val videoPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )

    val picturePermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val videoLaunch = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        Timber.v("權限回覆".title())

        var isPermission = true

        permissions.forEach { (s, b) ->

            Timber.w("$s - $b")

            if (!b) isPermission = false
        }

        if (isPermission) grantedPms() else deniedPms()
    }

    val picturePermissionsLaunch = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        Timber.v("權限回覆".title())

        var isPermission = true

        permissions.forEach { (s, b) ->

            Timber.w("$s - $b")

            if (!b) isPermission = false
        }

        if (isPermission) grantedPms() else deniedPms()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        kotlin.runCatching {
            mainActivity = activity as MainActivity
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun setToolbar(title: String) {
        getToolBar()?.apply { tvTitle.text = title }
    }

    fun setArrowToolbar(title: String) {

        getToolBar()?.apply {

            tvTitle.text = title
            ivArrow.visible()

            ivArrow.setOnClickListener { backPage() }
        }
    }

    /**
     * 返回鍵
     */
    fun backPage() {

        CoroutineScope(Dispatchers.Main).launch {
            mainActivity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    /**
     * BottomNavigationView
     */
//    fun showBnv() {
//        mainActivity?.findViewById<BottomNavigationView>(R.id.bnv)?.visible()
//    }

    /**
     * Toast
     */
    fun showToast(msg: String = ApiUrl.wait) {

        toast?.cancel()

        toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)

        CoroutineScope(Dispatchers.Main).launch {

            toast?.show()
        }
    }

    /**
     * Dialog Fragment
     */
    fun showProgress() {

        loadingDialog?.dismiss()
        loadingDialog = LoadingDialog()
        loadingDialog?.show(childFragmentManager, null)
    }

    fun closeProgress() {
        loadingDialog?.dismiss()
    }

    /**
     * 頁面整理
     */
    fun popFragment(fragment: Int) {

        CoroutineScope(Dispatchers.Main).launch {

            mainActivity?.findNavController(R.id.fcv)?.apply {

                popBackStack()
                navigate(fragment)
            }
        }
    }

    /**
     * 螢幕旋轉配置
     */
    fun setFullView() {

        mainActivity?.apply {

            findViewById<BottomNavigationView>(R.id.bnv)?.gone()
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    fun setVerView() {

        mainActivity?.apply {

            findViewById<BottomNavigationView>(R.id.bnv)?.visible()
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    /**
     * 是否有權限
     */
    fun hasPermissions(vararg permissions: String): Boolean {

        var isPermission = true

        Timber.v("檢查權限".title())

        for (permission in permissions) {

            isPermission = ContextCompat.checkSelfPermission(
                requireContext(), permission
            ) == PackageManager.PERMISSION_GRANTED

            Timber.w("$permission - $isPermission")

            if (!isPermission) isPermission = false
        }

        return isPermission
    }
}