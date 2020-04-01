package net.rhsrobotics;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import java.lang.reflect.InvocationHandler;

public class ClassReloader implements InvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Get an instance of the up-to-date dynamic class
        Object dynacode = getUpToDateInstance();

        // Forward the invocation
        return method.invoke(dynacode, args);
    }
}