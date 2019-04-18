package com.karacasoft.readsensors.sensorreader

import android.hardware.SensorManager
import com.karacasoft.readsensors.filewrite.Logger

abstract class BasicSensorReader: SensorReader {

    private var logger: Logger? = null

    override fun setLogger(logger: Logger) {
        this.logger?.close()
        this.logger = logger
        this.logger?.open()
    }

    override fun getLogger(): Logger? {
        return this.logger
    }
}