package org.firstinspires.ftc.robotcore.external;

import java.util.ArrayList;
import java.util.List;

public class Telemetry {
    private List<String> data = new ArrayList<>();


    public void addData(String name, Number stuff) {
        data.add(name + ": " + stuff.toString());
    }

    public void addData(String name, String stuff) {
        data.add(name + ": " + stuff);
    }

    public void addData(String name, Boolean stuff) {
        data.add(name + ": " + stuff.toString());
    }

    public void update() {
        for(int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }
        data = new ArrayList<String>();
    }
}