import java.io.*;

public class WriteMessage extends Thread {
    public WriteMessage() throws IOException {
        start();
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(Client.clientSocket.getOutputStream()))) {
            while (true) {
                if (!Client.clientSocket.isClosed()) {
                    String word = reader.readLine(); // ждём пока клиент что-нибудь напишет
                    if (word.equals("/exit")) {
                        out.write("/exit" + "\n");
                        break;
                    }
                    out.write(word + "\n"); // отправляем сообщение на сервер
                    out.flush();
                } else {
                    break;
                }
            }
            Client.exit();
        } catch (Exception exception) {
            Client.exit();
        }
    }
}
