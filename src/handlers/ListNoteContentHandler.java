package handlers;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import helpers.NoteQueryHelper;
import services.NoteService;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ListNoteContentHandler implements HttpHandler {

        public void handle(HttpExchange httpExchange) throws IOException {

            NoteService service = new NoteService();
            NoteQueryHelper helper = new NoteQueryHelper();

            Headers headers = httpExchange.getResponseHeaders();
            helper.addCorsHeaders(headers);

            Map<String,String> params = helper.queryToMap(httpExchange.getRequestURI().getQuery());

            String res = service.returnSpecificNote(params);

            httpExchange.sendResponseHeaders(200, res.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(res.getBytes());
            os.close();

        }
    }