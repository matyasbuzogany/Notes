import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Notes {

    public static void main(String[] args) throws IOException {

        if (args.length > 0) {

            switch (args[0]) {

                case "-add":
                    if (args.length == 3) {
                        createNote(args[1], args[2]);
                        System.out.println("Note: " + args[1] + " created successfully!");
                    } else {
                        System.out.println("Add command takes 2 parameters: title and content!");
                    }
                    break;

                case "-list":
                    if (args.length == 1) {
                        listNotes();
                        break;
                    } else if (args.length == 2) {
                        listSpecificNote(args[1]);
                        break;
                    } else {
                        System.out.println("List command takes 0 or 1 parameter: title!");
                    }
                    break;

                case "-startServer":
                    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
                    server.createContext("/notes", new ListAllNotesHandler());
                    server.createContext("/listNote", new ListNoteContentHandler());
                    server.createContext("/addNote", new AddNoteHandler());
                    server.setExecutor(null); // creates a default executor
                    server.start();
                    break;

                default:
                    System.out.println("Invalid command!");
                    break;
            }

        } else {
            System.out.println("Parameter list empty!");
        }
    }


    public static void createNote(String title, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"/notes/" + title +".txt"));
            writer.write(content);
            writer.close();
        } catch (IOException ioException) {
            System.out.println("Creation failed!");
            ioException.printStackTrace();
        }
    }


    public static void listNotes() {
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


    public static void listSpecificNote(String title) {
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


    public static Map<String, String> queryToMap(String query){

        Map<String, String> result = new HashMap<String, String>();

        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }


    static class AddNoteHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Headers headers = httpExchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Headers","x-prototype-version,x-requested-with");
            headers.add("Access-Control-Allow-Methods","GET,POST");
            headers.add("Access-Control-Allow-Origin","*");

            Map <String,String>params = Notes.queryToMap(httpExchange.getRequestURI().getQuery());

            createNote(params.get("title"), params.get("content"));
            String response = "OK";

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


    static class ListAllNotesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Headers headers = httpExchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Headers","x-prototype-version,x-requested-with");
            headers.add("Access-Control-Allow-Methods","GET,POST");
            headers.add("Access-Control-Allow-Origin","*");



            String response ="";
            File fileFolder = new File(System.getProperty("user.dir") + "/notes");
            File[] files = fileFolder.listFiles();
            String toDel = System.getProperty("user.dir") + "/notes/";
            for (File file : files) {
                String fileName = file.toString().replaceAll(toDel, "");
                response = response + fileName +"\n";
            }

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


    static class ListNoteContentHandler implements HttpHandler {

        public void handle(HttpExchange httpExchange) throws IOException {
            Headers headers = httpExchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Headers","x-prototype-version,x-requested-with");
            headers.add("Access-Control-Allow-Methods","GET,POST");
            headers.add("Access-Control-Allow-Origin","*");

            Map <String,String>params = Notes.queryToMap(httpExchange.getRequestURI().getQuery());

            String res = "";
            File fileFolder = new File(System.getProperty("user.dir") + "/notes");
            File[] files = fileFolder.listFiles();
            for (File file : files) {
                if (file.toString().contains(params.get("title"))) {
                    res = Files.readString(Path.of(file.getPath()));
                }
            }

            httpExchange.sendResponseHeaders(200, res.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(res.getBytes());
            os.close();

        }
    }

}


