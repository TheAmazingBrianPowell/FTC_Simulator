package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class MyOpMode extends LinearOpMode {
    private DcMotor left, right;
    private ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() {
        left = hardwareMap.get(DcMotor.class, "backLeft");
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setDirection(DcMotor.Direction.FORWARD);
        right = hardwareMap.get(DcMotor.class, "backRight");
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setDirection(DcMotor.Direction.REVERSE);

	    waitForStart();

        time.reset();
        left.setPower(-1);
        right.setPower(-1);
        while(time.time() < 2 && opModeIsActive()) {
            telemetry.addData("time", time.toString());
            telemetry.update();
            idle();
        }
        left.setPower(-1);
        right.setPower(0);
        while(time.time() < 2.2 && opModeIsActive()) {
            idle();
        }
        left.setPower(1);
        right.setPower(1);
        while(time.time() < 5 && opModeIsActive()) {
            idle();
        }
        left.setPower(1);
        right.setPower(-1);
        while(time.time() < 8 && opModeIsActive()) {
            idle();
        }

        left.setPower(-0.5);
        right.setPower(-1);
        while(time.time() < 12 && opModeIsActive()) {
            idle();
        }
        left.setPower(0);
        right.setPower(0);
    }
}