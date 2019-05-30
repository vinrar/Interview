package Crawler;

import Model.Data;
import Model.Pages;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        ExecutorService executor = Executors.newFixedThreadPool(8);
        success.add(list.get(0).getAddress());
        DFS(list.get(0).getLinks(), map, executor);

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Success: " + success);
        System.out.println("Skipped: " + skipped);
        System.out.println("Errored: " + errored);
    }

    private static void DFS(String[] links, Map<String, Pages> map, ExecutorService executor) {

            for(int i = 0; i < links.length; i++) {
                String link = links[i];
                executor.submit(() -> {
                    if(visited.contains(link)) {
                        System.out.println("Links visited: " + link);
                        skipped.add(link);
                    } else {
                        System.out.println("newly visited: " + link);
                        visited.add(link);
                        if(map.containsKey(link)) {
                            System.out.println("successfully visited: " + link);
                            success.add(link);
                            DFS(map.get(link).getLinks(), map, executor);
                        } else {
                            System.out.println("errored: " + link);
                            errored.add(link);
                        }
                    }
            });
        };
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