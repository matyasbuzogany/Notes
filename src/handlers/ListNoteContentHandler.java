package handlers;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import helpers.NoteQueryHelper;
import services.NoteService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ListNoteContentHandler implements HttpHandler {

        public void handle(HttpExchange httpExchange) throws IOException {

            NoteService service = new NoteService();
            NoteQueryHelper helper = new NoteQueryHelper();

            Headers headers = httpExchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Headers","x-prototype-version,x-requested-with");
            headers.add("Access-Control-Allow-Methods","GET,POST");
            headers.add("Access-Control-Allow-Origin","*");

            Map<String,String> params = helper.queryToMap(httpExchange.getRequestURI().getQuery());

            String res = service.returnSpecificNote(params);

            httpExchange.sendResponseHeaders(200, res.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(res.getBytes());
            os.close();

        }
    }