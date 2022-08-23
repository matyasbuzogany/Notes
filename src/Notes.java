import java.io.*;
public class Notes {

    public static void main(String[] args) {

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
}
