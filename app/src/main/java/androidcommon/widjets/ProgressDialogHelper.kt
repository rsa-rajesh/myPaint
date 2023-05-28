package androidcommon.widjets

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.core.view.isGone
import com.bts.mypaint.R
import com.bts.mypaint.databinding.LayoutProgressDialogBinding
import com.bumptech.glide.Glide

object ProgressDialogHelper {
    fun getProgressDialog(context: Context): Dialog {
        return Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }
}

@SuppressLint("InflateParams")
fun Dialog.showProgress(message: String) {
    if (isShowing) {
        findViewById<TextView>(R.id.tvProgressMessage)?.apply {
            text = message
            isGone = message.isEmpty()
        }
    } else {

        val binding = LayoutProgressDialogBinding.inflate(LayoutInflater.from(context), null, false)
        setContentView(binding.root)

        Glide.with(context).load(R.drawable.loading_gif).into(binding.loadingProgress)

        binding.tvProgressMessage.apply {
            text = message
            isGone = message.isEmpty()
        }
        show()
    }
}

fun Dialog.dismissProgress() {
    if (isShowing) {
        dismiss()
    }
}