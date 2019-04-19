package com.karacasoft.readsensors.sensorreader

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class AccelerometerSensorReader(sensorManager: SensorManager) : BasicSensorReader(), SensorEventListener {


    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    init {
        sensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
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
        return "Accelerometer"
    }

    override fun readSensor(): Array<Float> {
        return floatArrayOf(x, y, z).toTypedArray()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }



}