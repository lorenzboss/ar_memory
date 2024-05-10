package ch.bfh.teamulrich.memory.viewmodels

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.max
import kotlin.math.sqrt

data class memoryState(var currentMagnitude: Double = 1.0, var maxMagnitude: Double = 1000.0)

class memoryViewModel(application: Application) : AndroidViewModel(application),
    SensorEventListener {

    private val _state = MutableStateFlow(memoryState())
    val state: StateFlow<memoryState> = _state

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
        val values = event?.values ?: return
        val currentMagnitude = sqrt(
            (values[0] * values[0] + values[1] * values[1] + values[2] * values[2]).toDouble()
        )
        val maxMagnitude = max(_state.value.maxMagnitude, _state.value.currentMagnitude)

        _state.value = memoryState(currentMagnitude, maxMagnitude)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}