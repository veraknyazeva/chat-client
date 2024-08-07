import java.io.*;

public class WriteMessage extends Thread {
    private final BufferedReader reader; // нам нужен ридер читающий с консоли, чтобы узнать что хочет сказать клиент

    public WriteMessage() throws IOException {
        reader = new BufferedReader(new InputStreamReader(System.in));
        Client.out = new BufferedWriter(new OutputStreamWriter(Client.clientSocket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!Client.clientSocket.isClosed()) {
                    String word = reader.readLine(); // ждём пока клиент что-нибудь напишет
                    if (word.equals("/exit")) {
                        Client.out.write("/exit" + "\n");
                        break;
                    }
                    Client.out.write(word + "\n"); // отправляем сообщение на сервер
                    Client.out.flush();
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
