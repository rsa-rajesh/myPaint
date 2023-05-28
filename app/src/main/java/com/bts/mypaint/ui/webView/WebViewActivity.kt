package com.bts.mypaint.ui.webView

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.WebSettings
import androidcommon.base.BaseActivity
import com.bts.mypaint.databinding.ActivityWebViewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class WebViewActivity : BaseActivity() {

    private val binding by lazy {
        ActivityWebViewBinding.inflate(layoutInflater)
    }
    companion object {
        fun newIntent(
            context: Context,
        ): Intent {
            return Intent(context, WebViewActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showProgress("Loading data from server...")
        doSomeNetworkStuff()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun doSomeNetworkStuff() {
        GlobalScope.launch(Dispatchers.IO) {
            val doc: Document? = Jsoup.connect("https://test.com.np/services/").get()
//            val ele: Elements = doc!!.select("div#_dynamic_list-3-164")
//            doc!!.getElementsByClass("oxy-header-wrapper oxy-overlay-header oxy-header").remove();
//            doc.getElementById("section-145-6").remove()
//            doc.getElementById("section-2-357").remove()
//            doc.getElementById("div_block-164-6").remove()
            Handler(Looper.getMainLooper()).post(Runnable {
//                binding.webView.loadData(ele.toString(), "text/html", "utf-8")
                val ws: WebSettings = binding.webView.settings
                ws.javaScriptEnabled = true
                binding.webView.loadDataWithBaseURL(
                    "https://test.com.np/services/",
                    doc.toString(),
                    "text/html",
                    "utf-8",
                    ""
                );
                hideProgress()
            })
        }
    }

}