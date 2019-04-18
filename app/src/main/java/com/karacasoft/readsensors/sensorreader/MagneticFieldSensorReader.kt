package com.karacasoft.readsensors.sensorreader

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class MagneticFieldSensorReader(sensorManager: SensorManager) : BasicSensorReader(), SensorEventListener {


    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    init {
        sensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    private var x: Float = 0f
    private var y: Float = 0f
    private var z: Float = 0f


    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            x = event.values[0]
            y = event.values[1]
            z = event.values[2]
        }
    }

    override fun getSensorName(): String {
        return "Magnetometer"
    }

    override fun readSensor(): Array<Float> {
        return floatArrayOf(x, y, z).toTypedArray()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }



}