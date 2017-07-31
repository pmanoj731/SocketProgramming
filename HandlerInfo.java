

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

class HandlerInfo implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
      String response = "Use /get?file=<fileNaem> to get the file";
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }