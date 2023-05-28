package com.bts.mypaint.ui.languageSelectionActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidcommon.RColor
import androidcommon.base.BaseActivity
import androidx.core.view.isGone
import com.bts.mypaint.data.Prefs
import com.bts.mypaint.databinding.ActivityLanguageSelectionBinding
import com.bts.mypaint.ui.OnBoardingActivity
import org.koin.android.ext.android.inject
import java.util.*


class LanguageSelectionActivity : BaseActivity() {

    private val prefs by inject<Prefs>()

    private val binding by lazy {
        ActivityLanguageSelectionBinding.inflate(layoutInflater)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LanguageSelectionActivity::class.java)
        }
    }

    private var selectedLanguage: String = "english"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (prefs.isFirstTime) {
            binding.ivBack.isGone = true
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    fun englishSelected(view: android.view.View) {
        binding.btConfirm.text = "CONFIRM"
        selectedLanguage = "english"
        binding.tvEnglish.typeface = Typeface.DEFAULT_BOLD
        binding.tvNepali.typeface = Typeface.DEFAULT
        binding.cvEnglish.setCardBackgroundColor(resources.getColor(RColor.background_light))
        binding.cvNepali.setCardBackgroundColor(resources.getColor(RColor.white))
    }

    fun nepaliSelected(view: android.view.View) {
        binding.btConfirm.text = "निश्चित गर्नुहोस्"
        selectedLanguage = "nepali"
        binding.tvEnglish.typeface = Typeface.DEFAULT
        binding.tvNepali.typeface = Typeface.DEFAULT_BOLD
        binding.cvEnglish.setCardBackgroundColor(resources.getColor(RColor.white))
        binding.cvNepali.setCardBackgroundColor(resources.getColor(RColor.background_light))
    }

    fun confirmClicked(view: android.view.View) {
        if (selectedLanguage.equals("nepali", ignoreCase = true)) {
            prefs.language = "ne"
        } else if (selectedLanguage.equals("english", ignoreCase = true)) {
            prefs.language = "en"
        }
        if (prefs.isFirstTime) {
            startActivity(OnBoardingActivity.newIntent(context = this))
        } else {
//            prefs.localeHasChanged = true
//            startActivity(MainActivityV2.newIntent(context = this))
        }
        this.finish()
    }
}