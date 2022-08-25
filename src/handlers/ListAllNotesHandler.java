package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.NoteService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class ListAllNotesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            NoteService service = new NoteService();

            Headers headers = httpExchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Headers","x-prototype-version,x-requested-with");
            headers.add("Access-Control-Allow-Methods","GET,POST");
            headers.add("Access-Control-Allow-Origin","*");

            String response = service.listAllNotes();

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }