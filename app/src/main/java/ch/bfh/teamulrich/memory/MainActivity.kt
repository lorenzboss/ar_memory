package ch.bfh.teamulrich.memory

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
import ch.bfh.teamulrich.memory.ui.BottomBarNavigation
import ch.bfh.teamulrich.memory.ui.theme.MemoryTheme
import ch.bfh.teamulrich.memory.views.WithPermission
import ch.bfh.teamulrich.memory.views.reader.QRCodeView
import com.google.accompanist.permissions.ExperimentalPermissionsApi

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MemoryTheme() {
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
                            startDestination = Screen.Reader.route,
                            Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Reader.route) { QRCodeView() }
                        }
                }
            }}
        }
    }
}
