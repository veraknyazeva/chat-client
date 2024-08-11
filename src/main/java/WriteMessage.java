import java.io.*;

public class WriteMessage extends Thread {
    private final BufferedReader reader; // нам нужен ридер читающий с консоли, чтобы узнать что хочет сказать клиент
    private final BufferedWriter out;

    public WriteMessage() throws IOException {
        reader = new BufferedReader(new InputStreamReader(System.in));
        out = new BufferedWriter(new OutputStreamWriter(Client.clientSocket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        try {
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
            reader.close();
            out.close();
            Client.exit();
        } catch (Exception exception) {
            Client.exit();
        }
    }
}
