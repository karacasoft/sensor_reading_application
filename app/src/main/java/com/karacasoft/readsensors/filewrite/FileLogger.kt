package com.karacasoft.readsensors.filewrite

import android.os.Environment
import android.util.Log
import java.io.File
import java.io.PrintWriter

class FileLogger(private val filename: String): Logger {

    private var writer: PrintWriter? = null

    override fun log(str: String) {
        writer?.println(str)
        writer?.flush()
    }

    override fun open() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val dir = File(Environment.getExternalStorageDirectory(), "sensorReader")
            Log.d("FileLogger","Wow dir %s".format(dir.absolutePath))
            if (!dir.mkdirs()) {
                Log.e("FileLogger", "Directory not created")
            }
            val file = File(dir, "%s.txt".format(filename))
            writer = PrintWriter(file)
        } else {
            Log.d("FileLogger","Not mounted lolol")
        }
    }

    override fun close() {
        writer?.close()
    }

}