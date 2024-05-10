package ch.bfh.teamulrich.metaldetector.views.reader

import android.content.Context
import android.net.Uri
import android.util.Log
import android.util.Size
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

@Composable
fun QRCameraView(onQRCodeResult: (Uri, String) -> Unit) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Camera preview
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    // TODO: build ImageCapture with settings of your choosing
//    val imageCapture: ImageCapture = remember {
//    }

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()
    // TODO: build ImageAnalysis with settings of your choosing
//    val imageAnalysis = remember {
//    }

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()

        // TODO: set analyzer in the ImageAnalysis using QRCodeAnalyzer

        // TODO: bind different providers to CameraProvider
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
//            imageCapture,
//            imageAnalysis
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    AndroidView(factory = {
        previewView
    }, modifier = Modifier.fillMaxSize())
}
