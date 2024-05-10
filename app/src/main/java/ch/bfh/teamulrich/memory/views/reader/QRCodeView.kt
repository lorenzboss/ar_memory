package ch.bfh.teamulrich.memory.views.reader

import android.graphics.Bitmap
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.bfh.teamulrich.memory.R
import ch.bfh.teamulrich.memory.viewmodels.QRCodeViewModel
import ch.bfh.teamulrich.memory.viewmodels.ReceiveQRCode


@Composable
fun QRCodeView(viewModel: QRCodeViewModel = viewModel()) {
    val code = viewModel.qrCode

    val scanQrCodeLauncher =
        rememberLauncherForActivityResult(contract = ReceiveQRCode(), onResult = { result ->
            if (result != null) {
                viewModel.setCode(result)
            }
        })

    Surface(color = MaterialTheme.colors.surface, modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            if (code.isBlank()) {
                TableScreen(scanQrCodeLauncher, viewModel)
            } else {
                Text(text = "Scanned code:")
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = code, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        viewModel.saveQRCode()
                        viewModel.clear()
                    },
                    modifier = Modifier.width(164.dp)
                ) {
                    Text(text = "Save QR Code")
                }
                Button(onClick = { viewModel.clear() }, modifier = Modifier.width(164.dp)) {
                    Text(text = "Reset")
                }
            }
        }
    }
}


@Composable
fun TableScreen(
    scanQrCodeLauncher: ManagedActivityResultLauncher<Unit, String?>,
    viewModel: QRCodeViewModel
) {
    val rows = viewModel.rows
    val refreshTrigger = viewModel.refreshTrigger

    Column(
        modifier = Modifier.padding(top = 30.dp, bottom = 15.dp).fillMaxSize()
    ) {

        Table(rows, scanQrCodeLauncher, viewModel)
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Button(onClick = { viewModel.addRow() }) {
                Text("Add")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.removeRow() }) {
                Text("Remove")
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Spacer(modifier = Modifier.weight(1f))

        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = { viewModel.sendQRCodesToLogApp() }) {
            Text("Send to LogBook")
        }



    }
}

@Composable
fun Table(
    rows: Int,
    scanQrCodeLauncher: ManagedActivityResultLauncher<Unit, String?>,
    viewModel: QRCodeViewModel
) {
    Column {
        (0 until rows).forEach { row ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
                    .background(Color.hsl(0f, 0f, 0.9f))
            ) {
                Cell(row, 0, Modifier.weight(1f), scanQrCodeLauncher, viewModel)
                Cell(row, 1, Modifier.weight(1f), scanQrCodeLauncher, viewModel)
            }
        }
    }
}

@Composable
fun Cell(
    row: Int,
    column: Int,
    modifier: Modifier = Modifier,
    scanQrCodeLauncher: ManagedActivityResultLauncher<Unit, String?>,
    viewModel: QRCodeViewModel
) {
    val key = "$row-$column"
    Box(
        modifier
            .border(1.dp, Color.Black)
            .height(130.dp)
            .padding(top = 12.dp),
    ) {

        viewModel.getQRCode(key)?.let { qrCode ->
            Text(text = qrCode, textAlign = TextAlign.Center, fontSize = 20.sp, modifier = Modifier.align(Alignment.TopCenter))
        }
        Row(
            modifier = Modifier.align(Alignment.BottomEnd),
            verticalAlignment = Alignment.Bottom
        )
        {

            IconButton(onClick = { viewModel.deleteQRCode(key) }) {
                Icon(
                    painterResource(id = R.drawable.round_warning_24),
                    contentDescription = "Delete QR-Code"
                )
            }

            IconButton(
                onClick = {
                    viewModel.setKey(key)
                    scanQrCodeLauncher.launch()
                },
            ) {
                Icon(
                    painterResource(id = R.drawable.round_add_a_photo_24),
                    contentDescription = "Add QR-Code"
                )
            }
        }
    }
}
