import java.io.IOException;
import java.util.*;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> added = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Give me a string!");
        } else if (url.getPath().equals("/school")) {
            num += 1;
            return String.format("University of California, San Diego");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    added.add(parameters[1]);
                    return String.format("Added to list: %s", parameters[1]);
                }
            }
            else if (url.getPath().contains("/search")) {
                ArrayList<String> contained = new ArrayList<>();
                String[] parameters = url.getQuery().split("=");
                for(String search: added) {
                    if(search.contains(parameters[1])) {
                        contained.add(search);
                    }
                }
                return String.format("words containing %s: %s", parameters[1], contained.toString()); //to String prints out data structures
    
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
