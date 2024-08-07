import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ReadMessage extends Thread {

    public ReadMessage() throws IOException {
        Client.in = new BufferedReader(new InputStreamReader(Client.clientSocket.getInputStream()));
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!Client.clientSocket.isClosed()) {
                    if (Client.in.ready()) {
                        String message = Client.in.readLine(); // ждём, что скажет сервер
                        writeLog(message);
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

    private void writeLog(String message) {
        synchronized (Client.writer) {
            String messageForLog = message + "\n";
            try {
                Client.writer.write(messageForLog.getBytes(StandardCharsets.UTF_8));
            } catch (Exception ex) {
                System.out.println("Данные не сохранились");
            }
        }
    }
}
