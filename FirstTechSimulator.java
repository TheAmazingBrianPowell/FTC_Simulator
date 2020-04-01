import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.stream.Stream;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

import org.firstinspires.ftc.teamcode.MyOpMode;

import javax.swing.text.AbstractDocument;
import javax.swing.text.Element;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
//import java.net.URLClassLoader;
//import java.net.URL;
//
//import java.lang.reflect.InvocationHandler;

//import net.rhsrobotics.ClassReloader;

public class FirstTechSimulator implements Runnable {

    private static int x = 0;
    private static int y = 0;
    private static int movex = 250;
    private static int movey = 200;
    private static Field card2 = new Field(200,200);
    private static JTextField motor3 = new JTextField("backLeft");
    private static JTextField motor4 = new JTextField("backRight");
    private static JButton ok = new JButton("Init");
    private static JEditorPane codeArea = new JEditorPane();
    private static File classesDir = new File("org/firstinspires/ftc/teamcode");
    private static ClassLoader parentLoader = MyOpMode.class.getClassLoader();
    private static Class<MyOpMode> cls1;
    private static MyOpMode opMode = new MyOpMode();
    private static JEditorPane errors = new JEditorPane();
    private static final StringWriter err = new StringWriter();

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
                Integer errorCode = 0;
                try {
                    FileWriter fw = new FileWriter(myFile);
                    fw.write(codeArea.getText());
                    fw.close();
                    errorCode = com.sun.tools.javac.Main.compile(new String[] {"org/firstinspires/ftc/teamcode/MyOpMode.java"}, new PrintWriter(err));

//                    try (URLClassLoader loader1 = new URLClassLoader(new URL[] { classesDir.toURI().toURL() },  parentLoader)) {
//                        cls1 = (Class<MyOpMode>) loader1.loadClass("org.firstinspires.ftc.teamcode.MyOpMode");
//                        opMode = cls1.getConstructor().newInstance();
//                    } catch(Exception except) {
//                        except.printStackTrace();
//                    }

                } catch(IOException exc) {
                    System.out.println("I/O Exception!!!!!");
                } catch(Exception compileError) {
                     compileError.printStackTrace();
                }
                errors.setText(errorCode == 0 ? "<pre style = 'color:green;'>Code saved! Restart the program to run the updated code!</pre>" : "<pre style='color:red;'>There is an error in your code:</pre><br><pre>" + err + "</pre>");
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

        JScrollPane scrollPane = new JScrollPane(codeArea);
        JScrollPane errorScroll = new JScrollPane(errors);
        codeArea.setEditorKit(new CustomEditorKit());
        codeArea.setText(readLineByLine("org/firstinspires/ftc/teamcode/MyOpMode.java"));
        JPanel JBottom = new JPanel();
        JBottom.setPreferredSize(new Dimension(1000, 200));
        JBottom.setLayout(new BorderLayout());
        JBottom.add(save, BorderLayout.NORTH);
        errors.setEditable(false);
        errors.setContentType("text/html");
        JBottom.add(errorScroll, BorderLayout.CENTER);
        codeArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        card3.setLayout(new BorderLayout());
        card3.add(scrollPane);
        card3.add(JBottom, BorderLayout.SOUTH);
        card3.add(fileName, BorderLayout.NORTH);


        tabbedPane.addTab("Code", card3);
        tabbedPane.addTab("Init", card1);
        tabbedPane.addTab("Run", card2);

        pane.add(tabbedPane, BorderLayout.CENTER);
        pane.add(ok, BorderLayout.NORTH);
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
            opMode.opModeActive = false;
            opMode.isStopped = true;
            opMode.isStarted = false;
        }
    }

    public static void main(String[] args) {
//        try (URLClassLoader loader1 = new URLClassLoader(new URL[] { classesDir.toURI().toURL() },  parentLoader)) {
//            cls1 = (Class<MyOpMode>) loader1.loadClass("org.firstinspires.ftc.teamcode.MyOpMode");
//            opMode = cls1.getConstructor().newInstance();
            //InvocationHandler handler = new ClassReloader();
            //opMode = (MyOpMode) Proxy.newProxyInstance(MyOpMode.class.getClassLoader(), new Class[] { MyOpMode.class }, handler);
//        } catch(Exception except) {
//            except.printStackTrace();
//        }

        JFrame frame = new JFrame("FTC Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FirstTechSimulator simulator = new FirstTechSimulator();
        simulator.addComponentToPane(frame.getContentPane());

        frame.setPreferredSize(new Dimension(1000, 800));
        frame.pack();
        frame.setVisible(true);

        Thread t = new Thread(simulator);
        t.start();

        while (true) {
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

    public void setRobotPosition(int robotX2, int robotY2) {
        robotX = robotX2;
        robotY = robotY2;
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

        double centerX;
        double centerY;
        if (power1 != power2) {
            centerX = (x[2] + x[3]) / 2 + (power1 + power2) / ((power1 - power2) / ((x[3] - x[2]) / 2));
            centerY = (y[2] * (((power2 + power1) / (power2 - power1)) / 2 + 0.5) + y[3] * (((power1 + power2) / (power1 - power2)) / 2 + 0.5));
            double c1 = Math.sqrt((x[0] - centerX) * (x[0] - centerX) + (y[0] - centerY) * (y[0] - centerY));
            double c2 = Math.sqrt((x[1] - centerX) * (x[1] - centerX) + (y[1] - centerY) * (y[1] - centerY));
            double r = (c1 + c2) / 2;
            double deltaS;
            if((power1 <= 0 && power2 >= 0) || (power1 > 0) && power2 > 0) {
                deltaS = -3;
            } else {
                deltaS = 3;
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
            centerX = (x[2] + x[3]) / 2;
            centerY = (y[2] + y[3]) / 2;
        }

        g2d.setPaint(new Color(0,0,0));
        Polygon backWheels = new Polygon(x6,y6,4);
        g2d.fillPolygon(backWheels);

        g2d.setPaint(new Color(70,70,70));
        Polygon robot = new Polygon(x3, y3, 4);
        g2d.fillPolygon(robot);

        g2d.setPaint(Color.RED);
        g2d.fillOval((int) centerX - 5, (int) centerY - 5,10,10);
    }

}

class CustomEditorKit extends StyledEditorKit {

    private static final long serialVersionUID = 1L;

    @Override
    public ViewFactory getViewFactory() {
        return new CustomViewFactory(super.getViewFactory());
    }
}

class CustomViewFactory implements ViewFactory {

    private ViewFactory defaultViewFactory;

    CustomViewFactory(ViewFactory defaultViewFactory) {
        this.defaultViewFactory = defaultViewFactory;
    }

    @Override
    public View create(Element elem) {
        if (elem != null && elem.getName().equals(AbstractDocument.ParagraphElementName)) {
            return new CustomParagraphView(elem);
        }
        return defaultViewFactory.create(elem);
    }
}

class CustomParagraphView extends ParagraphView {

    public final short MARGIN_WIDTH_PX = 30;

    private Element thisElement;

    private Font font;

    public CustomParagraphView(Element elem) {
        super(elem);
        thisElement = elem;
        this.setInsets((short) 0, (short) 0, (short) 0, (short) 0);
    }

    @Override
    protected void setInsets(short top, short left, short bottom, short right) {
        super.setInsets(top, (short) (left + MARGIN_WIDTH_PX), bottom, right);
    }

    @Override
    public void paintChild(Graphics g, Rectangle alloc, int index) {
        super.paintChild(g, alloc, index);
        if (index > 0) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pad left so the numbers align
        int lineNumber = getLineNumber() + 1;
        String lnStr = String.format("%3d", lineNumber);

        font = font != null ? font : new Font(Font.MONOSPACED, Font.PLAIN, 12);
        g2d.setFont(font);

        int x = alloc.x - getLeftInset();
        int y = alloc.y + alloc.height - 3;
        g2d.setPaint(new Color(150,150,150));
        g2d.fillRect(x,y - 12, MARGIN_WIDTH_PX - 5, 25);
        g2d.setPaint(Color.WHITE);
        g2d.drawString(lnStr, x, y);
    }

    private int getLineNumber() {
        Element root = getDocument().getDefaultRootElement();
        int len = root.getElementCount();
        for (int i = 0; i < len; i++) {
            if (root.getElement(i) == thisElement) {
                return i;
            }
        }
        return 0;
    }
}

/*var x = [200,250,250,200];
var y = [200,200,250,250];

var prevX = [200,250,250,200];
var prevY = [200,200,250,250];
var prevAngle = 0;

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
        if((power1 <= 0 && power2 >= 0) || (power1 > 0) && power2 > 0) {
            deltaS = -3;
        } else {
            deltaS = 3;
        }
        fill(255,0,0);
        ellipse(centerX, centerY,10,10);
        for(var i = 0; i < 4; i++) {
            prevX[i] = x[i];
            prevY[i] = y[i];
            prevAngle = currentAngle;
            var x2 = Math.cos(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (x[i] - centerX) - Math.sin(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (y[i] - centerY) + centerX;
            y[i] = Math.cos(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (y[i] - centerY) + Math.sin(deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2)) * (x[i] - centerX) + centerY;
            x[i] = x2;
        }
        currentAngle += deltaS / r * ((Math.abs(power1) + Math.abs(power2)) / 2);
    } else {
        var slope;
        var angle;
        fill(255,0,0);
        ellipse((x[0] + x[1]) / 2, (y[0] + y[1]) / 2,10,10);
        if(y[0] !== y[1]) {
            slope = (x[1] - x[0]) / (y[0] - y[1]);
            if(y[0] < y[1]) {
                if(x[0] > x[1]) {
                    currentAngle = Math.atan(-slope) - Math.PI;
                } else {
                    currentAngle = Math.atan(-slope) - Math.PI;
                }
            } else {
                if(x[0] > x[1]) {
                    currentAngle = Math.atan(-slope);
                } else {
                    currentAngle = Math.atan(-slope);
                }
            }
            println(currentAngle);
        } else {
            currentAngle = -Math.PI / 2;
        }
        for(var i = 0; i < 4; i++) {
            prevX[i] = x[i];
            prevY[i] = y[i];
            x[i] -= Math.cos(currentAngle) * -(power1 + power2) * 3;
            y[i] += Math.sin(currentAngle) * -(power1 + power2) * 3;
        }
    }
};
var collided = [false, false];
draw = function() {
    background(255);
    var left = 0;
    var right = 0;
    if(input[LEFT] && !collided[0]) {
        left = -1;
    }
    if(input[RIGHT] && !collided[1]) {
        right = -1;
    }
    move(right,left);
    var didCollide = false;
    for(var i = 0; i < 4; i++) {
        if(x[i] <= 0 || x[i] >= 400 || y[i] <= 0 || y[i] >= 400) {
            didCollide = true;
            collided[i % 2] = true;
            break;
        } else {
            collided[i % 2] = false;
        }
    }
    if(didCollide) {
        for(var i = 0; i < 4; i++) {
            x[i] = prevX[i];
            y[i] = prevY[i];
        }
        currentAngle = prevAngle;
    }
    noFill();
    quad(x[0],y[0],x[1],y[1],x[2],y[2],x[3],y[3]);
};

*/