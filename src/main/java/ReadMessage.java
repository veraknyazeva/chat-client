import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadMessage extends Thread {

    public ReadMessage() throws IOException {
        start();
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(Client.clientSocket.getInputStream()));) {
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
        } catch (Exception exception) {
            Client.exit();
        }
    }
}
