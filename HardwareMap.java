package FTC_simulator;

import java.util.List;
import java.util.ArrayList;

public class HardwareMap {

    public List<DcMotor> dcMotorList = new ArrayList<>();
    public List<Servo> servoList = new ArrayList<>();

    public <T extends HardwareDevice> T get (Class<T> type, String name) {
        try {
            T device = type.getDeclaredConstructor(String.class).newInstance(name);
            if(device instanceof DcMotor) dcMotorList.add((DcMotor)device);
            if(device instanceof Servo) servoList.add((Servo)device);
            return device;
        } catch(Exception e) {
            return null;
        }
    }
}