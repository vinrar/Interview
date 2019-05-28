package Crawler;

import Model.Page1;
import Model.Pages;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        Pages pages = new Pages();

        try {
            Main main = new Main();
            File file = main.getFileFromResources("internet_1.json");
            System.out.println(file.toString());
            objectMapper.writeValue(file, pages);
        } catch (IOException e) {
            System.out.println("Error while parsing.");
            return;
        }
        System.out.println(pages);
    }

    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

}