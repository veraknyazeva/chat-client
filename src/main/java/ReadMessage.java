import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ReadMessage extends Thread {
    private final BufferedReader in;


    public ReadMessage() throws IOException {
        in = new BufferedReader(new InputStreamReader(Client.clientSocket.getInputStream()));
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!Client.clientSocket.isClosed()) {
                    if (in.ready()) {
                        String message = in.readLine(); // ждём, что скажет сервер
                        Client.writeLog(message);
                        System.out.println(message);
                    }
                } else {
                    break;
                }
            }
            in.close();
        } catch (Exception exception) {
            Client.exit();
        }
    }
}
