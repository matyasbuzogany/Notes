package services;

import models.Note;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteService {

    private List<Note> allNotes = new ArrayList<>();

    public NoteService() {
        loadNotesToList();
    }

    public void loadNotesToList() {
        try {
            File fileFolder = new File(System.getProperty("user.dir") + "/notes");
            File[] files = fileFolder.listFiles();
            String toDel = System.getProperty("user.dir") + "/notes/";

            for (File file : files) {
                String fileName = file.toString().replaceAll(toDel, "");
                BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/notes/" + fileName));
                String line;
                StringBuilder content = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

                Note note = new Note(fileName, content.toString());
                allNotes.add(note);
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }


    public void createNote(String title, String content) {
        try {
            Note note = new Note(title, content);
            allNotes.add(note);
            BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"/notes/" + note.getTitle() +".txt"));
            writer.write(note.getContent());
            writer.close();
        } catch (IOException ioException) {
            System.out.println("Creation failed!");
            ioException.printStackTrace();
        }
    }


    public void listNotesToCommandLine() {

        for (Note note : allNotes) {
            System.out.println("Title: " + note.getTitle() + "  -> Content: " + note.getContent());
        }
    }


    public String listAllNotes() {
        StringBuilder res = new StringBuilder();

        for (Note note : allNotes) {
            res.append(note.getTitle() + "\n");
        }
        return res.toString();
    }


    public void listSpecificNoteToCommandLine(String title) {

        for (Note note : allNotes) {
            if (note.getTitle().equals(title)) {
                System.out.println("Content: " + note.getContent());
            }
        }
    }


    public String returnSpecificNote(Map<String,String> params) throws IOException {
        String response = "";

        for (Note note : allNotes) {
            if (note.getTitle().contains(params.get("title"))) {
                response = note.getContent();
            }
        }
        return response;
    }
}
