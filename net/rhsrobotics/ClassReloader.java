package net.rhsrobotics;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import org.firstinspires.ftc.teamcode.MyOpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ClassReloader {
    public Class<?> reload(Class<?> myClass) {
        try {
            System.out.printf("my class is Class@%x%n", myClass.hashCode());
            System.out.println("reloading");
            URL[] urls={myClass.getProtectionDomain().getCodeSource().getLocation()};
            ClassLoader delegateParent = myClass.getClassLoader().getParent();

            try(URLClassLoader cl = new URLClassLoader(urls, delegateParent)) {
                Class<?> reloaded = cl.loadClass(myClass.getName());
                System.out.printf("reloaded my class: Class@%x%n", reloaded.hashCode());
                System.out.println(myClass.getName());
                System.out.println("Different classes: " + (myClass != reloaded));
                return reloaded;
            }
        } catch(IOException exc) {
            System.out.println("I/O Exception!!!!!");
        } catch(ClassNotFoundException exce) {
            System.out.println("Class MyOpMode not found!!!");
            exce.printStackTrace();
        } catch(Exception compileError) {
            compileError.printStackTrace();
        }
        return null;
    }
}