package ch.bfh.teamulrich.metaldetector.viewmodels

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import kotlin.math.max
import kotlin.math.sqrt

data class MetalDetectorState(var currentMagnitude: Double = 1.0, var maxMagnitude: Double = 1000.0)

class MetalDetectorViewModel(application: Application) : AndroidViewModel(application),
    SensorEventListener {

    var state by mutableStateOf(MetalDetectorState())
        private set

    private val sensorManager = application.getSystemService(SensorManager::class.java)
    private val magneticFieldSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    init {
        sensorManager.registerListener(
            this,
            this.magneticFieldSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // TODO: obtain magnetic sensor reading and update variable 'state'
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}
