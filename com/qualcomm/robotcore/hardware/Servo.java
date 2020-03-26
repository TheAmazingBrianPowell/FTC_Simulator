package com.qualcomm.robotcore.hardware;

public class Servo extends HardwareDevice {
    private String deviceName;
    Servo(String deviceName) {
        this.deviceName = deviceName;
    }
}