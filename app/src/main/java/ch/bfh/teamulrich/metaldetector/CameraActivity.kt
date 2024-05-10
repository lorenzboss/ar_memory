package ch.bfh.teamulrich.metaldetector

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ch.bfh.teamulrich.metaldetector.viewmodels.ReceiveQRCode
import ch.bfh.teamulrich.metaldetector.views.reader.QRCameraView

class CameraActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRCameraView(onQRCodeResult = { _, value ->
                // TODO: set result and extra for the intent to be sent to LogBook app
                finish()
            })
        }

    }
}
