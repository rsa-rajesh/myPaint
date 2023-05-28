package com.bts.mypaint.ui.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidcommon.RString
import androidcommon.base.BaseActivity
import androidx.core.text.htmlEncode
import androidx.core.text.parseAsHtml
import com.bts.mypaint.databinding.ActivityAboutAppBinding
import com.bts.mypaint.ui.colorGenerate.FandeckActivity

class AboutAppActivity : BaseActivity() {

    private val binding by lazy {
        ActivityAboutAppBinding.inflate(layoutInflater)
    }
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AboutAppActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()

    }

    private fun initViews() {
        with(binding) {
            val version = getString(RString.app_version)
            tvAppVersion.text = "version $version"
            tvAboutApp.text=getString(RString.about_app_html).parseAsHtml()
            ivBack.setOnClickListener {
                startActivity(FandeckActivity.newIntent(this@AboutAppActivity))
                this@AboutAppActivity.finish()
            }
        }
    }
}