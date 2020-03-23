package FTC_simulator-master;

public abstract class LinearOpMode {
    public HardwareMap hardwareMap = new HardwareMap();
    public abstract void runOpMode();
    public boolean opModeActive = false;
    public Telemetry telemetry = new Telemetry();
    public void waitForStart() {
        while(!opModeIsActive()) {
            idle();
        }
    }
    public boolean opModeIsActive() {
        idle();
        return opModeActive;
    }

    public boolean isStopRequested() {
        return !opModeActive;
    }
    public void idle() {
        try {
            Thread.sleep(2);
        } catch(Exception e) {

        }
    }

}