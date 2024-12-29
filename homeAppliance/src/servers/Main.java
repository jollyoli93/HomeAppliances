package servers;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;

class Main {
  private static final int PORT = 8080;

  private void startServer() {
    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
      server.createContext("/", new RootHandler());
      server.createContext("/admin", new AdminHandler());
      server.createContext("/appliances", new ApplianceHandler());
      server.setExecutor(null);
      server.start();
      System.out.println("The server is listening on port " + PORT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {

    Main main = new Main();
    main.startServer();
  }

}