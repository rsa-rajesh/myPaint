package androidcommon.base

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidcommon.extension.dialogOnForeverDenied
import androidcommon.extension.dialogShowRationale
import androidcommon.extension.hasPermission
import androidcommon.extension.openSettings
import androidcommon.widjets.ProgressDialogHelper
import androidcommon.widjets.dismissProgress
import androidcommon.widjets.showProgress
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bts.mypaint.data.Prefs
import com.bts.mypaint.utils.ContextUtils
import org.koin.android.ext.android.inject
import java.util.*

abstract class BaseActivity : AppCompatActivity() {
    private var whichPermission = Manifest.permission.INTERNET
    private var onPermissionGranted: () -> Unit = {}
    private val prefs by inject<Prefs>()

    private val progressDialog: Dialog by lazy {
        ProgressDialogHelper.getProgressDialog(this)
    }

    override fun attachBaseContext(newBase: Context) {
        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, Locale(prefs.language))
        super.attachBaseContext(localeUpdatedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    open fun showProgress(message: String = "Loading…") {
        progressDialog.showProgress(message)
    }

    open fun hideProgress() {
        progressDialog.dismissProgress()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    onPermissionGranted.invoke()
                }
                shouldShowRequestPermissionRationale(whichPermission) -> {
                    dialogShowRationale { askPermissionAgain() }
                }
                else -> {
                    dialogOnForeverDenied { openSettings() }
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    open fun askPermission(whichPermission: String, onPermissionGranted: () -> Unit) {
        this.whichPermission = whichPermission
        this.onPermissionGranted = onPermissionGranted
        if (hasPermission(whichPermission)) {
            onPermissionGranted.invoke()
        } else {
            requestPermissionLauncher.launch(whichPermission)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askPermissionAgain() {
        requestPermissionLauncher.launch(whichPermission)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

}