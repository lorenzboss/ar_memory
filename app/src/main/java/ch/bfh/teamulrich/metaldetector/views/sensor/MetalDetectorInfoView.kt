package ch.bfh.teamulrich.metaldetector.views.sensor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import ch.bfh.teamulrich.metaldetector.ui.theme.MetalDetectorTheme
import ch.bfh.teamulrich.metaldetector.viewmodels.MetalDetectorState
import kotlin.math.roundToInt

@Composable
fun MetalDetectorInfoView(state: MetalDetectorState, modifier: Modifier = Modifier) {
    Column {
        Row {
            // TODO: display current magnetic reading
        }
        Row {
            // TODO: display maximum magnetic reading
        }
    }
}

@Preview
@Composable
fun MetalDetectorInfoViewPreview() {
    MetalDetectorTheme {
        MetalDetectorInfoView(state = MetalDetectorState(), modifier = Modifier.fillMaxWidth())
    }
}