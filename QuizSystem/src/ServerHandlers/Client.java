package ServerHandlers;



import Frames.LoginFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",2021);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new PrintWriter(socket.getOutputStream(),true));
        new LoginFrame(in,out);
    }

}
