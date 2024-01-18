package com.jotangi.twinsSum.ui.fragment

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.jotangi.twinsSum.databinding.FragmentMeetingRoomBinding
import com.jotangi.twinsSum.ui.BaseFragment
import com.jotangi.twinsSum.utils.Const
import timber.log.Timber

class MeetingRoomFragment : BaseFragment() {

    private lateinit var binding: FragmentMeetingRoomBinding
    override fun getToolBar() = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeetingRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFullView()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val url = arguments?.getString(Const.webUrl, "") ?: ""
        Timber.w("url: ${url}")

        setWeb(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWeb(url: String) {

        binding.web.apply {

            with(settings) {

                javaScriptEnabled = true
                mediaPlaybackRequiresUserGesture = false
                domStorageEnabled = true
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)

                    binding.web.apply {

                        Timber.w("進度: $newProgress")
                        Timber.w("url: $url")
                    }
                }

                override fun onPermissionRequest(request: PermissionRequest?) {
                    request?.grant(request.resources)
                }
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view!!.loadUrl(url)
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    Timber.w("onPageFinished: $url")
                }
            }

            loadUrl(url)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        setVerView()
    }
}