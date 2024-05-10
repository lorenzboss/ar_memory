package ch.bfh.teamulrich.metaldetector.views.reader

import android.content.Context
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.bfh.teamulrich.metaldetector.viewmodels.QRCodeViewModel
import ch.bfh.teamulrich.metaldetector.viewmodels.ReceiveQRCode


@Composable
fun QRCodeView(viewModel: QRCodeViewModel = viewModel()) {
    val code = viewModel.qrCode

    val scanQrCodeLauncher = rememberLauncherForActivityResult(contract = ReceiveQRCode(), onResult = {
        if (it != null) {
            viewModel.setCode(it)
        }
    })

    Surface(color = MaterialTheme.colors.surface, modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            val context: Context = LocalContext.current
            if (code.isBlank()) {
                Button(
                    onClick = { scanQrCodeLauncher.launch() }
                ) {
                    Text(text = "Scan QR code")
                }
            } else {
                Text(text = "Scanned code:")
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = code, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        // TODO: use ViewModel to send result to LogBook, display errors that may happen
                    },
                    modifier = Modifier.width(164.dp)
                ) {
                    Text(text = "Log entry")
                }
                Button(onClick = { viewModel.clearCode() }, modifier = Modifier.width(164.dp)) {
                    Text(text = "Reset")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun QRCodeViewPreview() {
    QRCodeView()
}

