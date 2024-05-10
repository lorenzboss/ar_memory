package ch.bfh.teamulrich.metaldetector.views.reader

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

internal class QRCodeAnalyzer (
    private val onSuccess: ((Barcode) -> Unit) = {},
    private val onFailure: ((Exception) -> Unit) = {},
    private val onPassCompleted: ((Boolean) -> Unit) = {}
) : ImageAnalysis.Analyzer {
    private val barcodeScanner by lazy {
        val optionsBuilder = BarcodeScannerOptions.Builder().setBarcodeFormats(BARCODE_FORMAT)
        try {
            BarcodeScanning.getClient(optionsBuilder.build())
        } catch (e: Exception) { // catch if for some reason MlKitContext has not been initialized
            onFailure(e)
            null
        }
    }


    @Volatile
    private var failureOccurred = false
    private var failureTimestamp = 0L
    private var firstCall = true

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        if (imageProxy.image == null) return

        // TODO: image analysis
    }

    @ExperimentalGetImage
    @Suppress("UnsafeCallOnNullableType")
    private fun ImageProxy.toInputImage() =
        InputImage.fromMediaImage(image!!, imageInfo.rotationDegrees)

    companion object {
        const val BARCODE_FORMAT = Barcode.FORMAT_QR_CODE
    }
}
