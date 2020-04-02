package com.qualcomm.robotcore.eventloop.opmode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class LinearOpMode {
	public HardwareMap hardwareMap = new HardwareMap();

	public abstract void runOpMode();

	public boolean opModeActive = false;
	public boolean isStarted = false;
	public boolean isStopped = false;
	public Telemetry telemetry = new Telemetry();

	public void waitForStart() {
		while (!opModeIsActive()) {
			idle();
		}
	}

	public boolean opModeIsActive() {
		idle();
		return opModeActive;
	}

	public boolean isStopRequested() {
		idle();
		return isStopped;
	}

	public void idle() {
		try {
			Thread.sleep(2);
		} catch (Exception e) {

		}
	}

}