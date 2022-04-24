package ServerHandlers;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final static int PORT = 2021;

    public static void main(String[] args) throws IOException {
        File file = new File("Accounts.txt");
        File courseFile = new File("Courses");


        if (!courseFile.exists())
            courseFile.mkdirs();
        if (!file.exists())
            file.createNewFile();


        ServerSocket server = new ServerSocket(PORT);

        while(true){
            System.out.println("Server is waiting for connection!");
            Socket client = server.accept();
            System.out.println("Client connected!");
            new ClientThread(client).start();
        }
    }
}
