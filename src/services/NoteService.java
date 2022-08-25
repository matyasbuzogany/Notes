package services;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class NoteService {

    public NoteService() {
    }

    public void createNote(String title, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"/notes/" + title +".txt"));
            writer.write(content);
            writer.close();
        } catch (IOException ioException) {
            System.out.println("Creation failed!");
            ioException.printStackTrace();
        }
    }


    public void listNotesToCommandLine() {
        try {
            File fileFolder = new File(System.getProperty("user.dir") + "/notes");
            File[] files = fileFolder.listFiles();

            for (File file : files) {
                System.out.println(file);
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }

    public String listAllNotes() {
        StringBuilder res = new StringBuilder();
        String response;

        File fileFolder = new File(System.getProperty("user.dir") + "/notes");
        File[] files = fileFolder.listFiles();
        String toDel = System.getProperty("user.dir") + "/notes/";

        for (File file : files) {
            String fileName = file.toString().replaceAll(toDel, "");
            res.append(fileName + "\n");
        }
        response = res.toString();
        return response;
    }


    public void listSpecificNoteToCommandLine(String title) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/notes/" + title +".txt"));
            String line;

            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    public String returnSpecificNote(Map<String,String> params) throws IOException {
        String response = "";

        File fileFolder = new File(System.getProperty("user.dir") + "/notes");
        File[] files = fileFolder.listFiles();

        for (File file : files) {
            if (file.toString().contains(params.get("title"))) {
                response = Files.readString(Path.of(file.getPath()));
            }
        }
        return response;
    }
}
