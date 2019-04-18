package com.karacasoft.readsensors.sensorreader

import com.karacasoft.readsensors.filewrite.Logger

interface SensorReader {
    fun getLogger(): Logger?
    fun setLogger(logger: Logger)
    fun getSensorName(): String
    fun readSensor(): Array<Float>
}