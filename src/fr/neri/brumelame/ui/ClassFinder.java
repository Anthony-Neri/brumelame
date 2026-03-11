package fr.neri.brumelame.ui;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.jar.*;

public class ClassFinder {

    public static List<Class<?>> getClasses(String packageName) throws Exception {

        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');

        Enumeration<URL> resources =
                Thread.currentThread().getContextClassLoader().getResources(path);

        while (resources.hasMoreElements()) {

            URL resource = resources.nextElement();

            if (resource.getProtocol().equals("file")) {
                classes.addAll(findClassesInDirectory(
                        new File(resource.toURI()), packageName));
            }

            else if (resource.getProtocol().equals("jar")) {

                JarURLConnection connection =
                        (JarURLConnection) resource.openConnection();

                JarFile jar = connection.getJarFile();

                classes.addAll(findClassesInJar(jar, path));
            }
        }

        return classes;
    }

    private static List<Class<?>> findClassesInDirectory(File directory,
                                                         String packageName) throws Exception {

        List<Class<?>> classes = new ArrayList<>();

        if (!directory.exists())
            return classes;

        for (File file : directory.listFiles()) {

            if (file.isDirectory()) {

                classes.addAll(findClassesInDirectory(
                        file, packageName + "." + file.getName()));
            }

            else if (file.getName().endsWith(".class")) {

                String className = packageName + '.'
                        + file.getName().replace(".class", "");

                classes.add(Class.forName(className));
            }
        }

        return classes;
    }

    private static List<Class<?>> findClassesInJar(JarFile jar, String path)
            throws Exception {

        List<Class<?>> classes = new ArrayList<>();

        Enumeration<JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {

            JarEntry entry = entries.nextElement();
            String name = entry.getName();

            if (name.startsWith(path)
                    && name.endsWith(".class")
                    && !entry.isDirectory()) {

                String className = name
                        .replace("/", ".")
                        .replace(".class", "");

                classes.add(Class.forName(className));
            }
        }

        return classes;
    }
}