import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import org.firstinspires.ftc.teamcode.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.stream.Stream;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

public class FirstTechSimulator implements Runnable {

    private static int x = 0;
    private static int y = 0;
    private static int movex = 250;
    private static int movey = 200;
    private static Field card2 = new Field(200,200);
    private static JTextField motor3 = new JTextField("backLeft");
    private static JTextField motor4 = new JTextField("backRight");
    private static JButton ok = new JButton("Init");
    private static boolean exiting = false;
    private static JTextArea codeArea = new JTextArea(readLineByLine("org/firstinspires/ftc/teamcode/MyOpMode.java"));
    private static MyOpMode opMode = new MyOpMode();

    private static String readLineByLine(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public void addComponentToPane(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel card1 = new JPanel() {
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += 100;
                return size;
            }
        };

        JPanel card3 = new JPanel() {
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += 100;
                return size;
            }
        };



//        JLabel fileLabel = new JLabel("Enter class name");
//        fileLabel.setBounds(450, 580, 200, 20);
//        JTextField file = new JTextField("MyAutonomous");
//        file.setBounds(400, 600, 200, 30);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(2);
                if(ok.getText().equals("Init")) {
                    ok.setText("Start");
                    opMode.isStarted = true;
                    opMode.isStopped = false;
                } else if(ok.getText().equals("Start")) {
                    opMode.opModeActive = true;
                    ok.setText("Stop");
                } else if(ok.getText().equals("Stop")) {
                    opMode.opModeActive = false;
                    opMode.isStopped = true;
                    opMode.isStarted = false;
                    ok.setText("Init");
                }
            }
        });

//        JTextField motor1 = new JTextField();
//        motor1.setBounds(100 + movex, 100 + movey, 100, 20);
//        JTextField motor2 = new JTextField();
//        motor2.setBounds(300 + movex, 100 + movey, 100, 20);

        motor3.setBounds(100 + movex, 300 + movey, 100, 20);

        motor4.setBounds(300 + movex, 300 + movey, 100, 20);

//        JLabel motor1Label = new JLabel("<html><body>Front-left motor<br>&nbsp&nbsp&nbsp&nbsp&nbsp(optional)</body></html>");
//        motor1Label.setBounds(100 + movex, 50 + movey, 200, 40);
//        JLabel motor2Label = new JLabel("<html><body>Front-right motor<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp(optional)</body></html>");
//        motor2Label.setBounds(300 + movex, 50 + movey, 200, 40);
        JLabel motor3Label = new JLabel("Back-left motor");
        motor3Label.setBounds(100 + movex, 250 + movey, 200, 40);
        JLabel motor4Label = new JLabel("Back-right motor");
        motor4Label.setBounds(300 + movex, 250 + movey, 200, 40);

        JLabel configuration = new JLabel("<html><body style = 'text-align:center;'><h1>FTC Simulator</h1><br><h2>Created By Brian Powell from Team 15342, Aries</h2><br><p>Set the configuration names for the motors below</p></body></html>");
        configuration.setBounds(275, 40, 1000, 200);

        JLabel fileName = new JLabel("MyOpMode.java");

        JButton save = new JButton("Save Code");

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File myFile = new File("org" + File.separator + "firstinspires" + File.separator + "ftc" + File.separator + "teamcode" + File.separator + fileName.getText());
                try {
                    FileWriter fw = new FileWriter(myFile);
                    fw.write(codeArea.getText());
                    fw.close();
                    runProcess("javac org/firstinspires/ftc/teamcode/MyOpMode.java");
//                    opMode = (MyOpMode) new ClassReloader().reload(MyOpMode.class).getConstructor().newInstance();
                } catch(IOException exc) {
                    System.out.println("I/O Exception!!!!!");
                } catch(ClassNotFoundException exce) {
                    System.out.println("Class " + myFile.getName() + " not found!!!");
                    exce.printStackTrace();
                } catch(Exception compileError) {
                    compileError.printStackTrace();
                }
            }
        });

        card1.setLayout(null);
//        card1.add(motor1);
//        card1.add(motor1Label);
//        card1.add(motor2);
//        card1.add(motor2Label);
        card1.add(motor3);
        card1.add(motor3Label);
        card1.add(motor4);
        card1.add(motor4Label);
        card1.add(configuration);

        card3.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(codeArea);
        codeArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        card3.add(scrollPane);
        card3.add(save, BorderLayout.SOUTH);
        card3.add(fileName, BorderLayout.NORTH);


        tabbedPane.addTab("Code", card3);
        tabbedPane.addTab("Init", card1);
        tabbedPane.addTab("Run", card2);

        pane.add(tabbedPane, BorderLayout.CENTER);
        pane.add(ok, BorderLayout.NORTH);
    }

    private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
    }

    private static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
        }
    }

    public void run() {
        while(true) {
            while (!opMode.isStarted) {
                opMode.idle();
            }
            try {
                opMode.runOpMode();
            } catch (Exception e) {
                System.out.println("Error in opMode");
            }
            card2.setRobotPosition(250,200);
            ok.setText("Init");
            opMode.isStarted = false;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FTC Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FirstTechSimulator simulator = new FirstTechSimulator();
        simulator.addComponentToPane(frame.getContentPane());

        frame.setPreferredSize(new Dimension(1000, 800));
        frame.pack();
        frame.setVisible(true);

        Thread t = new Thread(simulator);
        t.start();

        while (!exiting) {
            double powerLeft = 0;
            double powerRight = 0;
            for (int i = 0; i < opMode.hardwareMap.dcMotorList.size(); i++) {
                if (opMode.hardwareMap.dcMotorList.get(i).deviceName.equals(motor3.getText())) {
                    powerLeft = opMode.hardwareMap.dcMotorList.get(i).getPower();
                }
                if (opMode.hardwareMap.dcMotorList.get(i).deviceName.equals(motor4.getText())) {
                    powerRight = opMode.hardwareMap.dcMotorList.get(i).getPower();
                }
            }
            if (opMode.opModeIsActive()) card2.updateX(powerLeft, powerRight);
            else card2.updateX(0, 0);
            frame.repaint();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                return;
            }
        }
    }
}


class Field extends JPanel {
    double robotWheel1 = 0;
    double robotWheel2 = 0;
    double robotAngle = 0;
    double robotX = 0;
    double robotY = 0;
    double rotationAngle = 0;
    double x[] = new double[4];
    double y[] = new double[4];
    double x4[] = new double[4];
    double y4[] = new double[4];
    double currentAngle = 0;

    Field(int robotX, int robotY) {
        this.robotX = robotX;
        this.robotY = robotY;
        x[0] = robotX;
        y[0] = robotY;
        x[1] = robotX + 60;
        y[1] = robotY;
        x[2] = robotX + 60;
        y[2] = robotY + 76;
        x[3] = robotX;
        y[3] = robotY + 76;
        x4[0] = robotX - 5;
        y4[0] = robotY + 55;
        x4[1] = robotX + 65;
        y4[1] = robotY + 55;
        x4[2] = robotX + 65;
        y4[2] = robotY + 75;
        x4[3] = robotX - 5;
        y4[3] = robotY + 75;

    }

    public void updateX(double robotWheel1, double robotWheel2) {
        this.robotWheel1 = robotWheel1;
        this.robotWheel2 = robotWheel2;
    }

    public void setRobotPosition(int robotX, int robotY) {
        this.robotX = robotX;
        this.robotY = robotY;
        x[0] = robotX;
        y[0] = robotY;
        x[1] = robotX + 60;
        y[1] = robotY;
        x[2] = robotX + 60;
        y[2] = robotY + 76;
        x[3] = robotX;
        y[3] = robotY + 76;
        x4[0] = robotX - 5;
        y4[0] = robotY + 55;
        x4[1] = robotX + 65;
        y4[1] = robotY + 55;
        x4[2] = robotX + 65;
        y4[2] = robotY + 75;
        x4[3] = robotX - 5;
        y4[3] = robotY + 75;
        currentAngle = 0;
    }

//    public int[] getRobotPosition() {
//        int[] coordinate = {(int)robotX, (int)robotY};
//        return coordinate;
//    }

    @Override
    public void paint(Graphics g) {
        double power1 = robotWheel1;
        double power2 = robotWheel2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.setPaint(new Color(100, 100, 100));
        g2d.fillRect(200, 100, 600, 600);

        int x3[] = new int[4];
        int y3[] = new int[4];
        int x6[] = new int[4];
        int y6[] = new int[4];
        if (power1 != power2) {
            double centerX = (x[2] + x[3]) / 2 + (power1 + power2) / ((power1 - power2) / ((x[3] - x[2]) / 2));
            double centerY = (y[2] * (((power2 + power1) / (power2 - power1)) / 2 + 0.5) + y[3] * (((power1 + power2) / (power1 - power2)) / 2 + 0.5));
            double c1 = Math.sqrt((x[0] - centerX) * (x[0] - centerX) + (y[0] - centerX) * (y[0] - centerX));
            double c2 = Math.sqrt((x[1] - centerX) * (x[1] - centerX) + (y[1] - centerX) * (y[1] - centerX));
            double r = (c1 + c2) / 2;
            double deltaS;
            if(Math.abs(power1) > Math.abs(power2)) {
                deltaS = Math.abs(power1) / power1 * 3;
            } else {
                deltaS = Math.abs(power2) / power2 * 3;
            }
            for (int i = 0; i < 4; i++) {
                double x2 = Math.cos(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (x[i] - centerX) - Math.sin(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (y[i] - centerY) + centerX;
                y[i] = Math.cos(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (y[i] - centerY) + Math.sin(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (x[i] - centerX) + centerY;
                x[i] = x2;
                x3[i] = (int) (x[i] + 0.5);
                y3[i] = (int) (y[i] + 0.5);

                double x5 = Math.cos(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (x4[i] - centerX) - Math.sin(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (y4[i] - centerY) + centerX;
                y4[i] = Math.cos(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (y4[i] - centerY) + Math.sin(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (x4[i] - centerX) + centerY;
                x4[i] = x5;
                x6[i] = (int) (x4[i] + 0.5);
                y6[i] = (int) (y4[i] + 0.5);
            }
            currentAngle += deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2);
        } else {
            for(var i = 0; i < 4; i++) {
                x[i] -= Math.sin(currentAngle) * -(power1 + power2) * 3;
                y[i] += Math.cos(currentAngle) * -(power1 + power2) * 3;
                x3[i] = (int) (x[i] + 0.5);
                y3[i] = (int) (y[i] + 0.5);

                x4[i] -= Math.sin(currentAngle) * -(power1 + power2) * 3;
                y4[i] += Math.cos(currentAngle) * -(power1 + power2) * 3;
                x6[i] = (int) (x4[i] + 0.5);
                y6[i] = (int) (y4[i] + 0.5);
            }
        }

        g2d.setPaint(new Color(0,0,0));
        Polygon backWheels = new Polygon(x6,y6,4);
        g2d.fillPolygon(backWheels);

        g2d.setPaint(new Color(70,70,70));
        Polygon robot = new Polygon(x3, y3, 4);
        g2d.fillPolygon(robot);
    }

}

/*var x = [200,250,250,200];
var y = [200,200,250,250];

var input = [];
var deltaS = 0.1;

var currentAngle = 0;

keyPressed = function() {
    input[keyCode] = true;
};

keyReleased = function() {
    input[keyCode] = false;
};

var move = function(power1, power2) {
    if (power1 !== power2) {
        var centerX = (x[0] + x[1]) / 2 + (power1 + power2) / ((power1 - power2) / ((x[1] - x[0]) / 2));
        var centerY = (y[0] * (((power2 + power1) / (power2 - power1)) / 2 + 0.5) + y[1] * (((power1 + power2) / (power1 - power2)) / 2 + 0.5));
        var c1 = Math.round(sqrt(sq(x[0] - centerX) + sq(y[0] - centerY)));
        var c2 = Math.round(sqrt(sq(x[1] - centerX) + sq(y[1] - centerY)));
        var r = (c1 + c2) / 2;
        var deltaS;
        if(power1 > power2) {
            deltaS = Math.abs(power1) / power1 * 3;
        } else {
            deltaS = Math.abs(power2) / power2 * 3;
        }
        fill(255,0,0);
        ellipse(centerX, centerY,10,10);
        for(var i = 0; i < 4; i++) {
            var x2 = Math.cos(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (x[i] - centerX) - Math.sin(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (y[i] - centerY) + centerX;
            y[i] = Math.cos(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (y[i] - centerY) + Math.sin(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (x[i] - centerX) + centerY;
            x[i] = x2;
        }
        currentAngle += deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2);
    } else {
        for(var i = 0; i < 4; i++) {
            x[i] -= Math.sin(currentAngle) * -(power1 + power2) * 3;
            y[i] += Math.cos(currentAngle) * -(power1 + power2) * 3;
        }
    }
};
draw = function() {
    background(255);
    noFill();
    quad(x[0],y[0],x[1],y[1],x[2],y[2],x[3],y[3]);
    move(-0.1,-0.1);
    println(currentAngle);
};
*/