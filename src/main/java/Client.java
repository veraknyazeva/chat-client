import conf.ServerConfig;

import java.io.*;
import java.net.Socket;

public class Client {
    public static Socket clientSocket; //сокет для общения
    public static BufferedWriter out;
    public static BufferedReader in;
    public static final FileOutputStream writer;

    static {
        try {
            writer = new FileOutputStream("logs/client.log", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            clientSocket = new Socket("localhost", ServerConfig.PORT); // этой строкой мы запрашиваем
            ReadMessage readMessage = new ReadMessage();
            WriteMessage writeMessage = new WriteMessage();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void exit() {
        try {
            writer.close();
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception ex) {
        }
    }
}
