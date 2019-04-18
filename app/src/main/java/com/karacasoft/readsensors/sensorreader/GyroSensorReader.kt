package com.karacasoft.readsensors.sensorreader

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.karacasoft.readsensors.filewrite.Logger

class GyroSensorReader(sensorManager: SensorManager) : BasicSensorReader(), SensorEventListener {

    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    init {
        sensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    private var x: Float = 0.0f
    private var y: Float = 0.0f
    private var z: Float = 0.0f


    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            x = it.values[0]
            y = it.values[1]
            z = it.values[2]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // do nothing...
    }

    override fun getSensorName(): String {
        return "Gyro"
    }

    override fun readSensor(): Array<Float> {
        return floatArrayOf(x, y, z).toTypedArray()
    }


}