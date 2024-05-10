package ch.bfh.teamulrich.memory.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ch.bfh.teamulrich.memory.CameraActivity

class ReceiveQRCode: ActivityResultContract<Unit, String?>() {

    companion object {
        const val EXTRA_QR_CODE_VALUE = "qr_code_value"
    }

    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, CameraActivity::class.java)
    }

    override fun parseResult(resultCode: Int, result: Intent?): String? {
        return if (resultCode == Activity.RESULT_OK)
            result?.getStringExtra(EXTRA_QR_CODE_VALUE) else null
    }
}
