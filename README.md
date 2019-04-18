# sensor_reading_application
Reads sensors. Puts them into a file.

This application is written in Kotlin. It reads gyro-sensor accelerometer and magnetometer,
then puts the readings into a file created in the external storage. In the external storage,
it creates a directory named "SensorReader". It creates .txt files for each sensor and puts a
timestamp on filenames.
