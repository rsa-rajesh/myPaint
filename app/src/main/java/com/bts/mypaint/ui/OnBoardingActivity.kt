package com.bts.mypaint.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.bts.mypaint.R
import com.bts.mypaint.data.Prefs
import org.koin.android.ext.android.inject

class OnBoardingActivity : BaseActivity() {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, OnBoardingActivity::class.java)
        }
    }

    private val prefs by inject<Prefs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
    }

    public fun startActivity() {
//        startActivity(MainActivityV2.newIntent(this))
        this.finish()

    }
}