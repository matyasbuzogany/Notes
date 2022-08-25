package handlers;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerHandler {

    public ServerHandler() {
    }

    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/notes", new ListAllNotesHandler());
        server.createContext("/listNote", new ListNoteContentHandler());
        server.createContext("/addNote", new AddNoteHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
