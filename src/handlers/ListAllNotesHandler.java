package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import helpers.NoteQueryHelper;
import services.NoteService;

import java.io.IOException;
import java.io.OutputStream;

public class ListAllNotesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            NoteService service = new NoteService();
            NoteQueryHelper helper = new NoteQueryHelper();

            Headers headers = httpExchange.getResponseHeaders();
            helper.addCorsHeaders(headers);

            String response = service.listAllNotes();

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }