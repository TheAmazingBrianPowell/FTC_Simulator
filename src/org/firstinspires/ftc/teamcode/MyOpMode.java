package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class MyOpMode extends LinearOpMode {
	private DcMotor left, right;
	private ElapsedTime time = new ElapsedTime();

	@Override
	public void runOpMode() {
		left = hardwareMap.get(DcMotor.class, "backLeft");
		left.setDirection(DcMotor.Direction.FORWARD);
		right = hardwareMap.get(DcMotor.class, "backRight");
		right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		waitForStart();

		time.reset();
		left.setPower(0.4);
		right.setPower(-0.8);
		while (time.time() < 2 && opModeIsActive()) {
			idle();
		}
		left.setPower(-1);
		right.setPower(0);
		while (time.time() < 2.2 && opModeIsActive()) {
			idle();
		}
		left.setPower(1);
		right.setPower(1);
		while (time.time() < 3 && opModeIsActive()) {
			idle();
		}
		left.setPower(1);
		right.setPower(-1);
		while (time.time() < 8 && opModeIsActive()) {
			idle();
		}

		left.setPower(1);
		right.setPower(-1);
		while (time.time() < 12 && opModeIsActive()) {
			idle();
		}
		left.setPower(1);
		right.setPower(0.8);
		while (time.time() < 15 && opModeIsActive()) {
			idle();
		}
		left.setPower(0);
		right.setPower(0);
	}
}
