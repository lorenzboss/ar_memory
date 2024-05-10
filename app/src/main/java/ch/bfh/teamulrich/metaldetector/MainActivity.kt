package ch.bfh.teamulrich.metaldetector

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.bfh.teamulrich.metaldetector.ui.BottomBarNavigation
import ch.bfh.teamulrich.metaldetector.ui.theme.MetalDetectorTheme
import ch.bfh.teamulrich.metaldetector.views.WithPermission
import ch.bfh.teamulrich.metaldetector.views.reader.QRCodeView
import ch.bfh.teamulrich.metaldetector.views.sensor.MetalDetectorView
import com.google.accompanist.permissions.ExperimentalPermissionsApi

/**
 * The main activity.
 */
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MetalDetectorTheme() {
                WithPermission(permission = Manifest.permission.CAMERA, noPermissionContent = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Button(onClick = { it.launchPermissionRequest() }) {
                            Text("Grant camera permission")
                        }
                    }
                }) {
                Scaffold(
                    bottomBar = {
                        BottomBarNavigation(navController = navController)
                    }
                ) {
                    innerPadding ->
                        NavHost(
                            navController,
                            startDestination = Screen.Sensor.route,
                            Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Sensor.route) { MetalDetectorView() }
                            composable(Screen.Reader.route) { QRCodeView() }
                        }
                }
            }}
        }
    }
}
