package com.karacasoft.readsensors

import android.content.Context
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.SeekBar
import com.karacasoft.readsensors.filewrite.FileLogger
import com.karacasoft.readsensors.sensorreader.AccelerometerSensorReader
import com.karacasoft.readsensors.sensorreader.GyroSensorReader
import com.karacasoft.readsensors.sensorreader.MagneticFieldSensorReader
import com.karacasoft.readsensors.sensorreader.SensorReader
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : ExternalStorageModifyingActivity(), SeekBar.OnSeekBarChangeListener {


    var measureInterval: Long = 50
    var reading: Boolean = false

    var sensorManager: SensorManager? = null

    val sensorReaders: MutableList<SensorReader> = ArrayList()

    val handler: Handler

    init {
        val handlerThread = HandlerThread("SENSOR_WRITE_THREAD")
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }


    fun startReading() {
        reading = true

        val timestamp = System.currentTimeMillis() / 1000
        sensorReaders.forEach {
            it.setLogger(FileLogger("%s__%s".format(it.getSensorName(), timestamp)))
        }

        handler.postDelayed(this::readSensorValues, measureInterval)
    }

    fun stopReading() {
        reading = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager?.let {
            sensorReaders.add(GyroSensorReader(it))
            sensorReaders.add(AccelerometerSensorReader(it))
            sensorReaders.add(MagneticFieldSensorReader(it))
        }


        btnStartStopMeasure.setOnClickListener {
            if(reading) {
                stopReading()
                btnStartStopMeasure.text = getString(R.string.start_measure)
            } else {
                startReading()
                btnStartStopMeasure.text = getString(R.string.stop_measure)
            }
        }

        seekBar.setOnSeekBarChangeListener(this)


    }

    fun writeSensorReadingsToFile(reader: SensorReader) {
        reader.getLogger()?.log(reader.readSensor().joinToString(separator=" "))
    }

    fun readSensorValues() {
        sensorReaders.forEach {
            writeSensorReadingsToFile(it)
        }
        if (reading) {
            handler.postDelayed(this::readSensorValues, measureInterval)
        }
    }

    override fun onStop() {
        super.onStop()
        stopReading()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        measureInterval = (progress + 50).toLong()
        txtMeasureInterval.text = getString(R.string.interval_text).format(measureInterval)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}
