import handlers.ServerHandler;
import services.NoteService;

import java.io.*;

public class Notes {

    public static void main(String[] args) throws IOException {

        NoteService noteService = new NoteService();

        if (args.length == 0) {
            System.out.println("Argument list empty!");
        }

        else if (args.length == 1) {
            if (args[0].equals( "-list")) {
                noteService.listNotesToCommandLine();
            } else if (args[0].equals( "-startServer")){
                ServerHandler handler = new ServerHandler();
                handler.startServer();
            } else {
                System.out.println("Unknown argument!");
            }
        }

        else if (args.length == 2) {
            if (args[0].equals("-list")) {
                noteService.listSpecificNoteToCommandLine(args[1]);
            } else {
                System.out.println("Unknown argument!");
            }
        }

        else if (args.length == 3) {
            if (args[0].equals( "-add")) {
                noteService.createNote(args[1], args[2]);
            } else {
                System.out.println("Unknown argument!");
            }
        }

        else {
            System.out.println("Too many parameters!");
        }
    }
}


