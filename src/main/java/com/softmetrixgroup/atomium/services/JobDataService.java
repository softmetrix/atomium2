package com.softmetrixgroup.atomium.services;

import com.softmetrixgroup.Step;
import com.softmetrixgroup.atomium.helpers.ChildFirstClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Component
public class JobDataService {

    public List<Step> getStepsFromJar(String jarFilePath) {
        List<Step> steps = new ArrayList<>();
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration allEntries = jarFile.entries();
            URL[] urls = {new URL("jar:file:" + jarFilePath + "!/")};
            ChildFirstClassLoader cl = new ChildFirstClassLoader(urls, ClassLoader.getSystemClassLoader());
            while (allEntries.hasMoreElements()) {
                JarEntry je = (JarEntry) allEntries.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                String className = je.getName().substring(0, je.getName().length() - 6);
                final String className2 = className.replace('/', '.');
                if (className2.endsWith("Step") && !className2.endsWith(".Step")) {
                    Class<?> c = cl.loadClass(className2);
                    Constructor<?> cons = c.getConstructor();
                    com.softmetrixgroup.Step currentStep = (com.softmetrixgroup.Step) cons.newInstance();
                    steps.add(currentStep);
                }
            }
        } catch (IOException e) {
            // TODO handle exception
        } catch (ClassNotFoundException e) {
            // TODO handle exception
        } catch (NoSuchMethodException e) {
            // TODO handle exception
        } catch (InvocationTargetException e) {
            // TODO handle exception
        } catch (IllegalAccessException e) {
            // TODO handle exception
        } catch (InstantiationException e) {
            // TODO handle exception
        }
        return steps;
    }
}
