package Crawler;

import Model.Data;
import Model.Pages;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Main {
    public static volatile Set<String> visited = new HashSet<>();
    static volatile Set<String> success = new HashSet<>();
    static volatile Set<String> skipped = new HashSet<>();
    static volatile Set<String> errored = new HashSet<>();

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data pages;

        try {
            Main main = new Main();
            File file = main.getFileFromResources("internet_1.json");
            pages = objectMapper.readValue(file, Data.class);
        } catch (IOException e) {
            System.out.println("Error while parsing.");
            return;
        }

        List<Pages> list = pages.getPages();
        Map<String, Pages> map = new HashMap<>();

        for(int i = 0; i < list.size(); i++) {
            map.put(list.get(i).getAddress(), list.get(i));
        }

        DFS(list.get(0).getLinks(), map);

        System.out.println("Success: " + success);
        System.out.println("Skipped: " + skipped);
        System.out.println("Errored: " + errored);
    }

    private static void DFS(String[] links, Map<String, Pages> map) {
        for(int i = 0; i < links.length; i++) {
            if(visited.contains(links[i])) {
                skipped.add(links[i]);
            } else {
                visited.add(links[i]);
                if(map.containsKey(links[i])) {
                    success.add(links[i]);
                    DFS(map.get(links[i]).getLinks(), map);
                } else {
                    errored.add(links[i]);
                }
            }
        }
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