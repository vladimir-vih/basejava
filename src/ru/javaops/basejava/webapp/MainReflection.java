package ru.javaops.basejava.webapp;

import ru.javaops.basejava.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Resume r = new Resume("Any name");
        Method resumeToString = r.getClass().getMethod("toString");
        System.out.println("Reflection method invoke result: " + resumeToString.invoke(r));
    }
}
