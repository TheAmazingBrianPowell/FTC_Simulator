package FTC_simulator;

public class DcMotor extends HardwareDevice {
    private RunMode currentMode;
    public String deviceName;
    private double currentPower = 0;
    private Direction direction = Direction.FORWARD;

    public void setMode(RunMode mode) {
        currentMode = mode;
    }

    public RunMode getMode() {
        return currentMode;
    }

    public enum RunMode {
        RUN_WITHOUT_ENCODER,
        RUN_USING_ENCODER,
        RUN_TO_POSITION,
        STOP_AND_RESET_ENCODER,
        @Deprecated RUN_WITHOUT_ENCODERS,
        @Deprecated RUN_USING_ENCODERS,
        @Deprecated RESET_ENCODERS;
    }

    public enum Direction {
        FORWARD,
        REVERSE
    }

    public enum ZeroPowerBehavior {
        UNKNOWN,
        BRAKE,
        FLOAT
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPower(double i) {
        try {
            Thread.sleep(2);
        } catch(Exception e) {

        }
        currentPower = i;
    }

    public double getPower() {
        return (direction == Direction.REVERSE) ? -currentPower : currentPower;
    }

    DcMotor(String deviceName) {
        this.deviceName = deviceName;
    }
}