package com.qualcomm.robotcore.hardware;

import java.util.ArrayList;
import java.util.List;

public class HardwareMap {

	public List<DcMotor> dcMotorList = new ArrayList<>();
	public List<Servo> servoList = new ArrayList<>();

	public <T extends HardwareDevice> T get(Class<T> type, String name) {
		try {
			T device = type.getDeclaredConstructor(String.class).newInstance(name);
			if (device instanceof DcMotor)
				dcMotorList.add((DcMotor) device);
			if (device instanceof Servo)
				servoList.add((Servo) device);
			return device;
		} catch (Exception e) {
			return null;
		}
	}
}