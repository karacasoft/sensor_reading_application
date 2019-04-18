package com.karacasoft.readsensors.filewrite

interface Logger {
    fun open()
    fun log(str: String)
    fun close();
}