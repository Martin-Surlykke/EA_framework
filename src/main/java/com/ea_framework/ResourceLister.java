package com.ea_framework;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceLister {

    public static List<String> listProblemFiles(String problemType) throws IOException, URISyntaxException {
        String folder = getProblemFolder(problemType);
        return listResources(folder);
    }

    public static List<String> listResources(String path) throws IOException, URISyntaxException {
        List<String> filenames = new ArrayList<>();
        URL dirURL = Thread.currentThread().getContextClassLoader().getResource(path);

        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            java.io.File folder = new java.io.File(dirURL.toURI());
            java.io.File[] files = folder.listFiles();
            if (files != null) {
                for (var file : files) {
                    filenames.add(file.getName());
                }
            }
        } else if (dirURL != null && dirURL.getProtocol().equals("jar")) {
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
            try (JarFile jar = new JarFile(jarPath)) {
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    String name = entries.nextElement().getName();
                    if (name.startsWith(path) && !name.equals(path + "/")) {
                        filenames.add(name.substring(path.length() + 1));
                    }
                }
            }
        }

        return filenames;
    }

    public static InputStream resolveProblemStream(String problemType, String resourceName) throws FileNotFoundException {
        File file = new File("src/main/resources/tspFiles/a280.tsp");

        return new FileInputStream(file);
    }

    private static String getProblemFolder(String problemType) {
        return switch (problemType.toLowerCase()) {
            case "tsp2d" -> "tspFiles";
            case "bitstringproblem" -> "bitStringFiles";
            default -> throw new IllegalArgumentException("Unknown problem type: " + problemType);
        };
    }

}