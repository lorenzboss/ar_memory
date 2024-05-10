package ch.bfh.teamulrich.metaldetector.viewmodels

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import ch.bfh.teamulrich.metaldetector.R
import org.json.JSONObject

class QRCodeViewModel(application: Application) : AndroidViewModel(application) {

    var error by mutableStateOf("")
    private set

    private fun clearError() {
        this.error = ""
    }

    var qrCode by mutableStateOf("")
        private set

    fun setCode(code: String) {
        this.qrCode = code
    }

    fun clearCode() {
        this.qrCode = ""
    }

    fun sendQRCodeToLogApp() {
        // send Intent to LogBook app with task ID and solution value
    }
}